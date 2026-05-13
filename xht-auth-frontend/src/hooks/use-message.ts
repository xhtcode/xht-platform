interface MessageImplements {
  info(title: string): void

  warning(title: string): void

  success(title: string): void

  error(title: string): void
}

export function useMessage() {
  class MessageClass implements MessageImplements {
    // 普通提示
    info(title: string): void {
      // @ts-ignore
      ElMessage.info(title)
    }

    // 警告提示
    warning(title: string): void {
      // @ts-ignore
      ElMessage.warning(title)
    }

    // 成功提示
    success(title: string): void {
      // @ts-ignore
      ElMessage({
        message: title,
        showClose: true,
        type: 'success',
      })
    }

    // 错误提示
    error(title: string): void {
      // @ts-ignore
      ElMessage({
        message: title,
        showClose: true,
        type: 'error',
      })
    }
  }
  return new MessageClass()
}
