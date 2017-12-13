'use strict';
define(['productSeek', 'services/titleService'], function(productSeek) {

	productSeek.controller('NotFoundCtrl', ['$translate', 'titleService', function($translate, titleService) {
		$translate('error.title').then(function(title) {
			titleService.setTitle(title);
		});
	}]);
});