import type {FormRules} from "element-plus";
import {LoginPassWordFromModel, LoginPhoneFromModel} from "@/service/model/system/login.model";
import {reactive} from "vue";

/**
 * 登录表单验证规则
 */
export const loginPassWordFromRules: FormRules<Required<LoginPassWordFromModel>> = {
    username: [
        {required: true, message: '请输入用户账号', trigger: ['blur', 'change']},
        {min: 2, max: 50, message: '用户账号长度为2-50个字符', trigger: ['blur', 'change']},
    ],
    password: [
        {required: true, message: '请输入用户密码', trigger: ['blur', 'change']},
        {min: 6, max: 30, message: '用户密码长度为6-30个字符', trigger: ['blur', 'change']},
    ],
    captchaCode: [{required: true, message: '请输入图形验证码', trigger: ['blur', 'change']}],
}

/**
 * 登录表单验证规则
 */
export const loginPhoneFromRules = reactive<FormRules<Required<LoginPhoneFromModel>>>({
    phone: [
        {required: true, message: '请输入用户手机号', trigger: ['blur', 'change']},
        {pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: ['blur', 'change']},
    ],
    phoneCode: [{required: true, message: '请输入短信验证码', trigger: ['blur', 'change']}],
    captchaCode: [{required: true, message: '请输入图形验证码', trigger: ['blur', 'change']}],
})
