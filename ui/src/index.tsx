import React from 'react'
import './imports/rxjs'
import { render } from 'react-dom'
import { Provider } from '@tesler-ui/core'
import { ConfigProvider } from 'antd'
import ruRu from 'antd/es/locale-provider/ru_RU'
import { reducers } from './reducers'
import { epics } from './epics'
import './index.css'
import AppLayout from './components/AppLayout/AppLayout'
import { axiosInstance } from './api/session'

const App = (
    <Provider customReducers={reducers} customEpics={epics} axiosInstance={axiosInstance()}>
        <ConfigProvider locale={ruRu}>
            <AppLayout />
        </ConfigProvider>
    </Provider>
)

render(App, document.getElementById('root'))
