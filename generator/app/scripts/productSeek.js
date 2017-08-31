'use strict';
define(['routes',
	'services/dependencyResolverFor',
	'i18n/i18nLoader!',
	'angular',
	'angular-route',
	'angular-bootstrap',
	'angular-sanitize',
	'bootstrap',
	'angular-translate',
    'angular-slick-carousel'],
	function(config, dependencyResolverFor, i18n) {
		var productSeek = angular.module('productSeek', [
			'ngRoute',
			'pascalprecht.translate',
			'ngSanitize',
			'ui.bootstrap',
            'slickCarousel'
		]);
		productSeek
			.config(
				['$routeProvider',
				'$controllerProvider',
				'$compileProvider',
				'$filterProvider',
				'$provide',
				'$translateProvider',
				'$qProvider',
                '$locationProvider',
				function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, $qProvider,  $locationProvider) {

					productSeek.controller = $controllerProvider.register;
					productSeek.directive = $compileProvider.directive;
					productSeek.filter = $filterProvider.register;
					productSeek.factory = $provide.factory;
					productSeek.service = $provide.service;
                    
					if (config.routes) {
						angular.forEach(config.routes, function(route, path) {
                            var resolved = dependencyResolverFor(['controllers/' + route.controller]);                            
                            angular.forEach(route.resolve, function(resolver, name) {
                                resolved[name] = resolver;
                            });

                            $routeProvider.when(path, {templateUrl: route.templateUrl, resolve: resolved, controller: route.controller, gaPageTitle: route.gaPageTitle});
						});
					}
                    
					if (config.defaultRoutePath) {
						$routeProvider.otherwise({redirectTo: config.defaultRoutePath});
					}

					$translateProvider.translations('preferredLanguage', i18n);
					$translateProvider.preferredLanguage('preferredLanguage');

					$qProvider.errorOnUnhandledRejections(false);
                    $locationProvider.hashPrefix('');
                    
				}])
            .run(['$rootScope', function($rootScope) {
                    $rootScope.isViewLoading = false;
                    $rootScope.$on('$routeChangeStart', function() {
                        $rootScope.isViewLoading = true;
                    });
                    $rootScope.$on('$routeChangeSuccess', function() {
                        $rootScope.isViewLoading = false;
                    });
                    $rootScope.$on('$routeChangeError', function() {
                        $rootScope.isViewLoading = false;
                    });            
            }])
            .value('url', 'http://localhost:8080')
            .value('categories', ['app', 'art', 'book', 'fashion', 'film', 'food', 'gadget', 'game', 'music', 'other'])
			.filter('urlencode', function() {
				return function(input) {
					return window.encodeURIComponent(input);
				}
			});
                return productSeek;
            }
);
