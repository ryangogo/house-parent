$("#account-submit").click(function () {
    $("#form-create-account").submit();//提交注册表单
});
$("#form-create-account").ajaxForm({
    beforeSubmit: function () {
        var result = validate_first();
        return result;
    },
    success: function (obj) {
        if (obj.status == 0) {
            layer.alert("注册成功");
            window.location.href = "";
        } else {
            layer.alert(obj.msg);
        }
    }
});

/**
 * 验证注册表单中的哪些字段为空
 * @returns {boolean}
 */
function validate_first() {
    var register_form = $("#form-create-account").serializeArray();
    var isExit = true;
    $.each(register_form, function () {
        if (this.value == null || this.value == "") {
            layer.alert($("input[name = '" + this.name + "']").attr("typeName") + "不能为空");
            isExit = false;
            return false;//跳出循false环
        }
    });
    if (isExit == true) {
        var realPathWord = $("#form-create-account-password").val();
        var fakePathWord = $("#form-create-account-confirm-password").val();
        if (realPathWord != fakePathWord) {
            layer.alert("两次密码输入不一致，请重新输入");
            isExit = false;
        }
    }
    return isExit;
}
