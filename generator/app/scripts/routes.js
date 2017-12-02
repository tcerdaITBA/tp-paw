'use strict';

define(function() {
    return {
        defaultRoutePath: '/404',
        routes: {
            '/': {
                templateUrl: 'views/home.html',
                controller: 'HomeCtrl',
                resolve: {
                    productsData: ['$route', 'restService', 'defaultSortCriteria', function($route, restService, defaultSortCriteria) {
                        var params = $route.current.params;
                        params.page = 1;
                        params.pageSize = params.pageSize || 10;
                        params.orderBy = params.orderBy || defaultSortCriteria.orderBy;
                        params.order = params.order || defaultSortCriteria.order;
                        return restService.getProducts(params);
                    }]
                }
            },
			'/search': {
				templateUrl: 'views/search.html',
				controller: 'SearchCtrl',
				resolve: {
                    productsData: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.searchProducts(params.q);
                    }],
					usersData: ['$route', 'restService', function($route, restService) {
						var params = $route.current.params;
                        return restService.searchUsers(params.q);
					}],
					query: ['$route', function($route) {
						var params = $route.current.params;
						return params.q;
					}]
                }
			},
            '/product/:id': {
                templateUrl: 'views/product.html',
                controller: 'ProductCtrl',
                resolve: {
                    product: ['$route', 'restService', function($route, restService) {
                        var params = $route.current.params;
                        return restService.getProduct(params.id);
                    }]
                }
            },
            '/profile/:id': {
                templateUrl: 'views/profile.html',
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
            },
			'/post': {
				templateUrl: 'views/post.html',
                controller: 'PostCtrl'
			},
            '/404': {
                templateUrl: '404.html',
                controller: 'NotFoundCtrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
