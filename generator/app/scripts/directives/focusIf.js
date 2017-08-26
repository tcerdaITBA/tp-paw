define(['productSeek'], function(productSeek) {

    'use strict';
	productSeek.directive('focusIf', function() {
		return function(scope, element, attrs) {
			scope.$watch(attrs.focusIf, function(newValue) {
				newValue && element[0].focus()
			});
		};
	});

});