window.keemun = window.keemun || {};
if (window.keemun.SearchForm == null) {
window.keemun.SearchForm = (function() {
    'use strict';

    var COMPONENT = 'keemun_search_form';

    var s = function SearchForm($wrapper, debug) {
        this._debug = _.isUndefined(debug) ? false : !!debug;
        this.$_wrapper = $wrapper;
        this._reps = [];
        this._statistics = undefined;
        this._repsKey = undefined;
        this._waitingRequests = 0;

        if (_.isUndefined($wrapper) || $wrapper.length === 0) {
            keemun.utils.error('Search form wrapper not found');
        } else {
            $(this.init.bind(this));
        }
    };

    s.prototype._setLoading = function() {
        this.getSubmitButton().prop('disable', true);
    };

    s.prototype._setLoadingDone = function() {
        var button = this.getSubmitButton();
        button.text(button.data('ready-text')).prop('disabled', false);
    };

    s.prototype._tryFinish = function() {
        var loadingDone = (this._waitingRequests <= 0);
        if (loadingDone) {
            this._setLoadingDone();
        }
        return loadingDone;
    };

    s.prototype.getWrapper = function() {
        return this.$_wrapper;
    };

    s.prototype.subSelect = function(name) {
        return this.getWrapper().find('.' + COMPONENT + '--' + name);
    };

    s.prototype.getForm = function() {
        return this.subSelect('element');
    };

    s.prototype.getSubmitButton = function() {
        return this.getForm().find(':submit');
    };

    s.prototype.getRealForm = function() {
        return this.subSelect('real-form');
    };

    s.prototype.setReps = function(reps) {
        this._reps = reps;
        this._repsKey = 'repo:' + reps.join(' repo:');
        return this;
    };

    s.prototype.getRepsKey = function() {
        return this._repsKey;
    };

    s.prototype.init = function() {
        if (this._debug) {
            keemun.utils.debug("Start in debug mode");
            this.getRealForm().show();
        }
        this._bindForm();

        this._setLoading();
        this._loadReps();
        this._loadStatistics();
    };

    s.prototype._loadReps = function() {
        var r = keemun.routes.controllers.Repositories.list();
        this._waitingRequests += 1;
        $.ajax({
            url: r.url,
            method: r.method,
            success: function(data) {
                var keys = [];
                _.forIn(data.repositories, function(rep) {
                    keys.push(rep.full_name);
                });
                this.setReps(keys);
                this._waitingRequests -= 1;
                this._tryFinish();
            }.bind(this)
        });
    };

    s.prototype._loadStatistics = function() {
        var r = keemun.routes.controllers.Statistics.statistics();
        this._waitingRequests += 1;
        $.ajax({
            url: r.url,
            method: r.method,
            success: function(data) {
                this._statistics = data;
                this._waitingRequests -= 1;
                this._tryFinish();
                this.showStatistics(
                    this._statistics.repositories.total,
                    this._statistics.sources.user,
                    this._statistics.sources.org,
                    keemun.State.lang
                );
            }.bind(this)
        });
    };

    s.prototype._bindForm = function() {
        var $form = this.getForm();
        var $rForm = this.getRealForm();

        $form.submit(function(event) {
            event.stopPropagation();
            var baseQ = this.subSelect('text-search').val();
            var q = baseQ + ' ' + this.getRepsKey();
            var $qField = $rForm.find('input[name="q"]');
            $qField.val(q);
            $rForm.submit();
            return false;
        }.bind(this));
    };

    s.prototype.showStatistics = function(repos, users, orgs, lang) {
        var _format = function() {
            var usersForm, orgsForm, by;
            lang = lang || "en";
            var reposForm = keemun.Messages.getNumeralByLangAndTranslationPrefix(repos, "Repo_stats.Repos", lang);
            if (users) {
                usersForm = keemun.Messages.getNumeralByLangAndTranslationPrefix(users, "Repo_stats.Users", lang);
            }
            if (orgs) {
                orgsForm = keemun.Messages.getNumeralByLangAndTranslationPrefix(orgs, "Repo_stats.Orgs", lang);
            }
            if (users && orgs) {
                by = keemun.Messages("Repo_stats.by.users_and_orgs", users, usersForm, orgs, orgsForm);
            } else if (users) {
                by = keemun.Messages("Repo_stats.by.users", users, usersForm);
            } else if (orgs) {
                by = keemun.Messages("Repo_stats.by.orgs", orgs, orgsForm);
            }
            if (by) {
                return keemun.Messages("Repo_stats.with_sources", repos, reposForm, by);
            } else {
                return keemun.Messages("Repo_stats.without_sources", repos, reposForm);
            }
        };
        var formatted = _format();
        if (this._debug) {
            keemun.utils.debug("statistics");
            keemun.utils.debug(this._statistics);
            keemun.utils.debug(formatted);
        }
        this.subSelect("stat").text(formatted).removeClass("hidden");
    };

    return s;

})();}
