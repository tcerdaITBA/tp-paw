/* global paths */
'use strict';
require.config({
    baseUrl: '/scripts',
    paths: {
        affix: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix',
        alert: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert',
        angular: '../../bower_components/angular/angular',
        'angular-route': '../../bower_components/angular-route/angular-route',
        'angular-translate': '../../bower_components/angular-translate/angular-translate',
        button: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/button',
        bootstrap: '../../bower_components/bootstrap/dist/js/bootstrap',
        carousel: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/carousel',
        collapse: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/collapse',
        dropdown: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/dropdown',
        'es5-shim': '../../bower_components/es5-shim/es5-shim',
        jquery: '../../bower_components/jquery/dist/jquery',
        json3: '../../bower_components/json3/lib/json3',
        modal: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/modal',
        moment: '../../bower_components/moment/moment',
        popover: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/popover',
        requirejs: '../../bower_components/requirejs/require',
        scrollspy: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/scrollspy',
        tab: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tab',
        tooltip: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tooltip',
        transition: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/transition',
        'angular-mocks': '../../bower_components/angular-mocks/angular-mocks',
        'angular-bootstrap': '../../bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'angular-sanitize': '../../bower_components/angular-sanitize/angular-sanitize',
        ngSanitize: '../../bower_components/ngSanitize/index',
        ngstorage: '../../bower_components/ngstorage/ngStorage',
        'slick-carousel': '../../bower_components/slick-carousel/slick/slick.min',
        'angular-slick-carousel': '../../bower_components/angular-slick-carousel/dist/angular-slick',
        spin: '../../bower_components/spin.js/spin',
        'angular-spinner': '../../bower_components/angular-spinner/dist/angular-spinner.min'

    },
    shim: {
        angular: {
            deps: [
                'jquery'
            ]
        },
        'angular-route': {
            deps: [
                'angular'
            ]
        },
        bootstrap: {
            deps: [
                'jquery',
                'modal'
            ]
        },
        modal: {
            deps: [
                'jquery'
            ]
        },
        tooltip: {
            deps: [
                'jquery'
            ]
        },
        'angular-translate': {
            deps: [
                'angular'
            ]
        },
        'angular-sanitize': {
            deps: [
                'angular'
            ]
        },
        'angular-bootstrap': {
            deps: [
                'angular'
            ]
        },
        'slick-carousel': {
            deps: [
                'jquery'
            ]
        },
        'angular-slick-carousel': {
            deps: [
                'slick-carousel',
                'angular'
            ]
        },
        spin: {
            deps: [
                'jquery'
            ]
        },
        'angular-spinner': {
            deps: [
                'spin',
                'angular'
            ]
        }
    },
    packages: [

    ]
});

if (paths) {
    require.config({
        paths: paths
    });
}

require([
        'angular',
        'productSeek',
        'controllers/IndexCtrl',
    ],
    function() {
        angular.bootstrap(document, ['productSeek']);
    }
);
