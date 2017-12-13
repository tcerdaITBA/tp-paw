'use strict';
define(['productSeek'], function(productSeek) {
	productSeek.directive('snackbar', function() {
		return {
			restrict: 'E',
			replace: true,
			transclude: true,
			template: '<div class="snackbar" ng-transclude></div>',
			scope: {id: '@'},
			link: function(scope, element, attrs) {
				var id = attrs.id;
				
				element.find('.snackbar').attr('id', id);
			}
		}
	});
});