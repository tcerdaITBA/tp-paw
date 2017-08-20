'use strict';
define(['productSeek'], function(productSeek) {

	productSeek.controller('IndexCtrl', function($scope, $location) {
		$scope.welcomeText = 'Welcome to your productSeek page';
        $location.path('#/');
	});
});
