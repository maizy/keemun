if (typeof window.hedgehog === 'undefined') { window.hedgehog = {}; }
if (typeof window.hedgehog.SearchForm === 'undefined') {
window.hedgehog.SearchForm = (function() {

    'use strict';

    var s = function SearchForm($wrapper, debug) {
        this._debug = _.isUndefined(debug) ? false : !!debug;
        this.$_wrapper = $wrapper;
        this._reps = [];
        this._repsKey = undefined;

        if ($wrapper.length === 0) {
            hedgehog.utils.error('Search form wrapper not found');
        } else {
            $(this.init.bind(this));
        }
    };

    s.prototype.getWrapper = function () {
        return this.$_wrapper;
    };

    s.prototype.getForm = function () {
        return this.$_wrapper.find('.search_form');
    };

    s.prototype.getRealForm = function () {
        return this.$_wrapper.find('.real_search_form');
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
            hedgehog.utils.debug("Start in debug mode");
            this.getRealForm().show();
        }
        this.getForm().find(':submit').prop('disabled', true);
        this._loadReps();
        this._bindForm();
    };

    s.prototype._loadReps = function () {
        var self = this;
        var r = hedgehog.routes.controllers.Repositories.list();
        $.ajax({
            url: r.url,
            method: r.method,
            success: function(data) {
                var keys = [];
                $.each(data.repositories, function(_, rep) {
                    keys.push(rep.full_name);
                });
                self.setReps(keys);
                self.getForm().find(':submit').prop('disabled', false);
            }
        });
    };

    s.prototype._bindForm = function () {
        var self = this;
        var $form = this.getForm();
        var $rForm = this.getRealForm();

        $form.submit(function(event) {
            event.stopPropagation();
            var baseQ = $form.find('input#text_search').val();
            var q = baseQ + ' ' + self.getRepsKey();
            var $qField = $rForm.find('input[name="q"]');
            $qField.val(q);
            $rForm.submit();
            return false;
       });
    };

    return s;

})();}
