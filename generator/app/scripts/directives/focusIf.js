define(['productSeek'], function(productSeek) {
    'use strict';
	productSeek.directive('focusIf', function() {
		return function(scope, element, attrs) {
			scope.$watch(attrs.focusIf, function(newValue) {
				if (newValue === true)
					element[0].focus();
				else
					element[0].blur();
			});
		};
	});
});