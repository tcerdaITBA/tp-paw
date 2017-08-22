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
            },
            '/profile/:id': {
                templateUrl: '/views/profile.html',
                controller: 'ProfileCtrl',
                resolve: {
                    user: ['$route', 'restService', function($route, restService){
                        var params = $route.current.params;
                        return restService.getUser(params.id);
                    }],
                    collections: ['$route', 'restService', function($route, restService){
                        var params = $route.current.params;
                        return restService.getCollectionsForUser(params.id);
                    }],
                    createdProducts: ['$route', 'restService', function($route, restService){
                        var params = $route.current.params;
                        return restService.getPostedByUser(params.id);
                    }],
                    votedProducts: ['$route', 'restService', function($route, restService){
                        var params = $route.current.params;
                        return restService.getVotedByUser(params.id);
                    }]
                }
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
