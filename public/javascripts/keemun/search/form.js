if (typeof window.keemun === 'undefined') { window.keemun = {}; }
if (typeof window.keemun.SearchForm === 'undefined') {
window.keemun.SearchForm = (function() {
    'use strict';

    var COMPONENT = 'keemun_search_form';

    var s = function SearchForm($wrapper, debug) {
        this._debug = _.isUndefined(debug) ? false : !!debug;
        this.$_wrapper = $wrapper;
        this._reps = [];
        this._repsKey = undefined;

        if (_.isUndefined($wrapper) || $wrapper.length === 0) {
            keemun.utils.error('Search form wrapper not found');
        } else {
            $(this.init.bind(this));
        }
    };

    s.prototype.getWrapper = function () {
        return this.$_wrapper;
    };

    s.prototype.subSelect = function(name) {
        return this.getWrapper().find('.' + COMPONENT + '--' + name);
    };

    s.prototype.getForm = function () {
        return this.subSelect('element');
    };

    s.prototype.getRealForm = function () {
        return this.subSelect('real-form');
    };

    s.prototype.setReps = function(reps) {
        this._reps = reps;
        this._repsKey = 'repo:' + reps.join(' repo:');
        return this;
    };

    s.prototype.getRepsKey = function () {
        return this._repsKey;
    };

    s.prototype.init = function () {
        if (this._debug) {
            keemun.utils.debug("Start in debug mode");
            this.getRealForm().show();
        }
        this.getForm().find(':submit').prop('disabled', true);
        this._loadReps();
        this._bindForm();
    };

    s.prototype._loadReps = function () {
        var r = keemun.routes.controllers.Repositories.list();
        $.ajax({
            url: r.url,
            method: r.method,
            success: function(data) {
                var keys = [];
                _.forIn(data.repositories, function(rep) {
                    keys.push(rep.full_name);
                });
                this.setReps(keys);
                this.getForm().find(':submit').prop('disabled', false);
            }.bind(this)
        });
    };

    s.prototype._bindForm = function () {
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

    return s;

})();}
