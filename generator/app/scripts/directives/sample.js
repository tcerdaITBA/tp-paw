'use strict';
define(['productSeek'], function(productSeek) {

	productSeek.directive('sample', function() {
		return {
			restrict: 'E',
			template: '<span>Sample</span>'
		};
	});
});
