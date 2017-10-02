define(['productSeek'], function(productSeek) {

    'use strict';
	productSeek.directive('focusIf', function() {
		return function(scope, element, attrs) {
			scope.$watch(attrs.focusIf, function(newValue) {
				// TODO: no anda el focus no se por que.
//				console.log(newValue);
//				console.log(element[0]);
				if (newValue === true) {
//					console.log("focusing!");
					element[0].focus();
				} else {
					element[0].blur();
//					console.log("Defocusing")
				}
			});
		};
	});

});