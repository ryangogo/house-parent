$(document).ready(function () {
    get_cities();
});
/**
 * 获取所有城市
 */
function get_cities() {
    $.ajax({
        url: "/house/cityList",
        async: false,
        success: function (data) {
            $.each(data.data, function (idx, obj) {
                alert(JSON.stringify(obj));
                $("#submit-city").append("<option value='" + obj.id + "'>" + obj.cityName + "</option>");
            })
        }
    })
}