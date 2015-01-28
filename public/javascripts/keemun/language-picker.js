window.keemun = window.keemun || {};
if (!window.keemun.LanguagePicker) {
window.keemun.LanguagePicker = (function() {
    'use strict';

    var COMPONENT = 'keemun_language-picker';

    var c = function LanguagePicker($wrapper) {
        this._$wrapper = $wrapper;

        if (_.isUndefined($wrapper) || $wrapper.length === 0) {
            keemun.utils.error('Language picker component\'s wrapper not found');
        } else {
            $(this.init.bind(this));
        }
    };

    c.prototype.getWrapper = function () {
        return this._$wrapper;
    };

    c.prototype.subSelect = function(name) {
        return this.getWrapper().find('.' + COMPONENT + '--' + name);
    };

    c.prototype.init = function () {
        this._bindLangs();
    };

    c.prototype._bindLangs = function () {
        this.subSelect('lang').on('click', this.onLangPicked.bind(this));
    };

    c.prototype.onLangPicked = function(event) {
        event.stopPropagation();
        var $lang = $(event.target),
            $form = this.subSelect('form'),
            $langInput = $form.find('input[name="lang"]');
        $langInput.val($lang.data('lang-code'));
        $form.submit();
    };

    return c;

})();}
