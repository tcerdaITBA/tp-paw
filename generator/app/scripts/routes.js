'use strict';

define(function() {
    return {
        defaultRoutePath: '/',
        routes: {
            '/': {
                templateUrl: '/views/home.html',
                controller: 'HomeCtrl',
                resolve: {
                    productsData: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        params.page = params.page || 1;
                        params.pageSize = params.pageSize || 10;
                        params.orderBy = params.orderBy || 'date';
                        params.order = params.order || 'desc';
                        return restService.getProducts($route.current.params);
                    }]
                }
            },
            '/about': {
                templateUrl: '/views/about.html',
                controller: 'about'
            },
            '/product/:id': {
                templateUrl: '/views/product.html',
                controller: 'ProductCtrl',
                resolve: {
                    product: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.getProduct(params.id);
                    }]
                }
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
