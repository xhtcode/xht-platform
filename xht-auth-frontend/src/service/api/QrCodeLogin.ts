import request from '@/utils/request'

/**
 * 生成二维码
 */
export function generateQrCode() {
  return request({
    method: 'get',
    url: '/qrCode/login/generateQrCode',
  })
}

/**
 * 获取二维码信息
 * @param qrCodeId 二维码id
 */
export function fetch(qrCodeId: string) {
  return request({
    method: 'get',
    url: `/qrCode/login/fetch/${qrCodeId}`,
  })
}
