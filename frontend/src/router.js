
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import SubscriptionSubscriptionManager from "./components/listers/SubscriptionSubscriptionCards"
import SubscriptionSubscriptionDetail from "./components/listers/SubscriptionSubscriptionDetail"

import PaymentPaymentManager from "./components/listers/PaymentPaymentCards"
import PaymentPaymentDetail from "./components/listers/PaymentPaymentDetail"


import ManagementSubscriptionManagementManager from "./components/listers/ManagementSubscriptionManagementCards"
import ManagementSubscriptionManagementDetail from "./components/listers/ManagementSubscriptionManagementDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/subscriptions/subscriptions',
                name: 'SubscriptionSubscriptionManager',
                component: SubscriptionSubscriptionManager
            },
            {
                path: '/subscriptions/subscriptions/:id',
                name: 'SubscriptionSubscriptionDetail',
                component: SubscriptionSubscriptionDetail
            },

            {
                path: '/payments/payments',
                name: 'PaymentPaymentManager',
                component: PaymentPaymentManager
            },
            {
                path: '/payments/payments/:id',
                name: 'PaymentPaymentDetail',
                component: PaymentPaymentDetail
            },


            {
                path: '/managements/subscriptionManagements',
                name: 'ManagementSubscriptionManagementManager',
                component: ManagementSubscriptionManagementManager
            },
            {
                path: '/managements/subscriptionManagements/:id',
                name: 'ManagementSubscriptionManagementDetail',
                component: ManagementSubscriptionManagementDetail
            },



    ]
})
