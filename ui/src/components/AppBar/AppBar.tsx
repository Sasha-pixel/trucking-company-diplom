import React from 'react'
import { ViewNavigation } from '../ViewNavigation/ViewNavigation'
import UserMenu from './components/UserMenu/UserMenu'
import styles from './AppBar.module.css'
import { useSelector } from 'react-redux'
import { AppState } from '../../interfaces/storeSlices'
import { Layout } from 'antd'
import cn from 'classnames'
import { WidgetTypes } from '@tesler-ui/core/interfaces/widget'

const showViewNavigationWidgetTypes: string[] = [WidgetTypes.SecondLevelMenu]

function AppBar() {
    const widgets = useSelector((state: AppState) => state.view.widgets)
    const showTabs = widgets?.some(widget => showViewNavigationWidgetTypes.includes(widget.type))
    return (
        <Layout.Header className={cn(styles.container, { [styles.withTabs]: showTabs })}>
            {showTabs && <ViewNavigation />}
            <UserMenu />
        </Layout.Header>
    )
}

export default React.memo(AppBar)
