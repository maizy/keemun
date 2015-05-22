window.keemun = window.keemun || {};
if (!window.keemun.Messages) {

    /**
     * usage:
     *      Message(key, [param0], [param1], ...)
     */
    window.keemun.Messages = (function() {
        'use strict';

        var translate = window._messages;
        window._messages = undefined;
        /**
         * num - suppose to be integer, not float
         * formOne - 1 яблоко
         * formTwo - 2 яблока
         * formFive - 5 яблок
         */
        translate.getRussianNumeral = function (num, formOne, formTwo, formFive) {
            num = Math.abs(num) % 100;
            if (num >= 11 && num <= 19) {
                return formOne;
            } else {
                switch (num % 10) {
                    case 1:
                        return formOne;
                    case 2:
                    case 3:
                    case 4:
                        return formTwo;
                }
            }
            return formFive;
        };

        translate.getNumeral = function (num, one, many) {
            if (num <= 1) {
                return one;
            }
            return many;
        };

        translate.getRussianNumeralByTranslationPrefix = function (num, prefix) {
            return translate.getRussianNumeral(
                num,
                translate(prefix + '.one'),
                translate(prefix + '.two'),
                translate(prefix + '.five')
            );
        };

        translate.getNumeralByTranslationPrefix = function (num, prefix) {
            return translate.getNumeral(
                num,
                translate(prefix + '.one'),
                translate(prefix + '.many')
            );
        };

        translate.getNumeralByLangAndTranslationPrefix = function (num, prefix, lang) {
            lang = lang || keemun.State.lang;
            if (lang === 'ru') {
                return translate.getRussianNumeralByTranslationPrefix(num, prefix);
            } else {
                return translate.getNumeralByTranslationPrefix(num, prefix);
            }
        };
        return translate;
    })();
}
