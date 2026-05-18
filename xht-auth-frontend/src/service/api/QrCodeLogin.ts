import request from '@/utils/request'

/**
 * 生成二维码
 */
export function generateQrCode() {
  return request({
    method: 'get',
    url: '/sso/qrCode/login/generate',
  })
}

/**
 * 获取二维码信息
 * @param qrCodeId 二维码id
 */
export function fetch(qrCodeId: string) {
  return request({
    method: 'get',
    url: `/sso/qrCode/login/fetch/${qrCodeId}`,
  })
}
