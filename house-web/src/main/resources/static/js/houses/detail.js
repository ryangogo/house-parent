$(document).ready(function () {
    $("#sendEmail").click(function () {
        $("#form-contact-agenta").submit();//提交注册表单
    });
    $("#form-contact-agenta").ajaxForm({
        beforeSubmit: function () {
            return true;
        },
        success: function (obj) {
            if (obj.status == 0) {
                layer.alert(obj.msg);
            }
        }
    });
});
