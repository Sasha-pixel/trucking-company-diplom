const stripTrailingSlash = (url: string) => (url.endsWith('/') ? url.slice(0, -1) : url)

const getRecordId = (href: string) => {
    const url = href.split('/')
    if (url[url.length - 2] === 'screen' || url[url.length - 2] === 'view') {
        return ''
    }
    return `/${url[url.length - 2]}/${url[url.length - 1]}`
}

const getTabKey = (tabUrl: string | undefined, applicationId: string) => tabUrl && `${stripTrailingSlash(tabUrl)}${applicationId}`

export { getRecordId, getTabKey }
