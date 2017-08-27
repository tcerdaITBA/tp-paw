define(['productSeek'], function(productSeek) {

    'use strict';
	productSeek.directive('loginRequiredPopover', function($compile) {
        return {
            restrict: 'A',
            scope: true,
            link: function (scope, element, attrs) {
                element.attr('uib-popover-template', "'views/popovers/loginRequired.html'");
                element.attr('popover-enable', attrs.loginRequiredPopover);
                element.attr('popover-trigger', "'outsideClick'");

                element.removeAttr('login-required-popover');
                
                $compile(element)(scope);
            }
        } 
    }); 
});