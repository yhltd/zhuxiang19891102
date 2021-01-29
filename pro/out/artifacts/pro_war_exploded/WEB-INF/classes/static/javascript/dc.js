let dc = {
    fun: {
        click: function (el, callback) {
            $(el).on('click', function () {
                callback();
            });
        },
        change: function (el, callback) {
            $(el).on('change', function () {
                callback();
            });
            return $(this).val();
        }
    },
    date: {
        format: function (date, format) {
            if (!date) return;
            if (!format)
                format = "yyyy-MM-dd";
            switch (typeof date) {
                case "string":
                    date = new Date(date.replace(/-/, "/"));
                    break;
                case "number":
                    date = new Date(date);
                    break;
            }
            if (!date instanceof Date) return;
            let dict = {
                "yyyy": date.getFullYear(),
                "M": date.getMonth() + 1,
                "d": date.getDate(),
                "H": date.getHours(),
                "m": date.getMinutes(),
                "s": date.getSeconds(),
                "MM": ("" + (date.getMonth() + 101)).substr(1),
                "dd": ("" + (date.getDate() + 100)).substr(1),
                "HH": ("" + (date.getHours() + 100)).substr(1),
                "mm": ("" + (date.getMinutes() + 100)).substr(1),
                "ss": ("" + (date.getSeconds() + 100)).substr(1)
            };
            return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
                return dict[arguments[0]];
            });
        },
        operation: function (date, num, type) {
            if (typeof date == 'string') date = new Date(date);

            switch (type) {
                case 'y':
                    date.setFullYear(date.getFullYear() + num);
                    break
                case 'M':
                    date.setMonth(date.getMonth() + num);
                    break;
                case 'd':
                    date.setDate(date.getDay() + num);
                    break;
                case 'h':
                    date.setHours(date.getHours() + num);
                    break;
                case 'm':
                    date.setMinutes(date.getMinutes() + num);
                    break;
                case 's':
                    date.setSeconds(date.getSeconds() + num);
                    break;
            }

            return date;
        }
    }
}