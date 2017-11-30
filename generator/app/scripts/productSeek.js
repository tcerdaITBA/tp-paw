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
	'angular-slick-carousel',
	'ngInfiniteScroll'],
	function(config, dependencyResolverFor, i18n) {
		var productSeek = angular.module('productSeek', [
			'ngRoute',
			'pascalprecht.translate',
			'ngSanitize',
			'ui.bootstrap',
			'slickCarousel',
			'infinite-scroll'
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
						document.body.scrollTop = document.documentElement.scrollTop = 0;
                    });
                    $rootScope.$on('$routeChangeError', function() {
                        $rootScope.isViewLoading = false;
                    });            
            }])
            .value('url', 'http://localhost:8080/api')
			.value('productImagesCount', 4)
			.value('productVideosCount', 2)
			.value('searchMinLength', 3)
			.value('searchMaxLength', 64)
            .value('categories', ['app', 'art', 'book', 'fashion', 'film', 'food', 'gadget', 'game', 'music', 'other'])
            // Explicitly setting paths in other to be revved
            .value('categoriesImage', {all: 'images/all.svg', app: 'images/app.svg', art: 'images/art.svg', book: 'images/book.svg', fashion: 'images/fashion.svg', film: 'images/film.svg',
        							   food: 'images/food.svg', gadget: 'images/gadget.svg', game: 'images/game.svg', music: 'images/music.svg', other:' images/other.svg'})
            .value('sortCriterias', [{orderBy: 'date', order: 'desc'}, {orderBy: 'votes', order: 'desc'}, {orderBy: 'alpha', order: 'asc'}])
            .value('defaultSortCriteria', {orderBy: 'date', order: 'desc'})
			.filter('urlencode', function() {
				return function(input) {
					return window.encodeURIComponent(input);
				}
			});
                
		return productSeek;
	}
);
