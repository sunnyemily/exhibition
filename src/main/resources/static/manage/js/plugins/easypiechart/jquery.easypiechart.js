/**!
 * easyPieChart
 * Lightweight plugin to render simple, animated and retina optimized pie charts
 *
 * @license
 * @author Robert Fleischmann <rendro87@gmail.com> (http://robert-fleischmann.de)
 * @version 2.1.5
 **/

(function(root, factory) {
    if(typeof exports === 'object') {
        module.exports = factory(require('jquery'));
    }
    else if(typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    }
    else {
        factory(root.jQuery);
    }
}(this, function($) {

    /**
     * Renderer to render the chart on a canvas object
     * @param {DOMElement} el      DOM element to host the canvas (root of the plugin)
     * @param {object}     options options object of the plugin
     */
    var CanvasRenderer = function(el, options) {
        var cachedBackground;
        var canvas = document.createElement('canvas');

        el.appendChild(canvas);

        if (typeof(G_vmlCanvasManager) !== 'undefined') {
            G_vmlCanvasManager.initElement(canvas);
        }

        var ctx = canvas.getContext('2d');

        canvas.width = canvas.height = options.size;

        // canvas on retina devices
        var scaleBy = 1;
        if (window.devicePixelRatio > 1) {
            scaleBy = window.devicePixelRatio;
            canvas.style.width = canvas.style.height = [options.size, 'px'].join('');
            canvas.width = canvas.height = options.size * scaleBy;
            ctx.scale(scaleBy, scaleBy);
        }

        // move 0,0 coordinates to the center
        ctx.translate(options.size / 2, options.size / 2);

        // rotate canvas -90deg
        ctx.rotate((-1 / 2 + options.rotate / 180) * Math.PI);

        var radius = (options.size - options.lineWidth) / 2;
        if (options.scaleColor && options.scaleLength) {
            radius -= options.scaleLength + 2; // 2 is the distance between scale and bar
        }

        // IE polyfill for Date
        Date.now = Date.now || function() {
            return +(new Date());
        };

        /**
         * Draw a circle around the center of the canvas
         * @param {strong} color     Valid CSS color string
         * @param {number} lineWidth Width of the line in px
         * @param {number} percent   Percentage to draw (float between -1 and 1)
         */
        var drawCircle = function(color, lineWidth, percent) {
            percent = Math.min(Math.max(-1, percent || 0), 1);
            var isNegative = percent <= 0 ? true : false;

            ctx.beginPath();
            ctx.arc(0, 0, radius, 0, Math.PI * 2 * percent, isNegative);

            ctx.strokeStyle = color;
            ctx.lineWidth = lineWidth;

            ctx.stroke();
        };

        /**
         * Draw the scale of the chart
         */
        var drawScale = function() {
            var offset;
            var length;

            ctx.lineWidth = 1;
            ctx.fillStyle = options.scaleColor;

            ctx.save();
            for (var i = 24; i > 0; --i) {
                if (i % 6 === 0) {
                    length = options.scaleLength;
                    offset = 0;
                } else {
                    length = options.scaleLength * 0.6;
                    offset = options.scaleLength - length;
                }
                ctx.fillRect(-options.size/2 + offset, 0, length, 1);
                ctx.rotate(Math.PI / 12);
            }
            ctx.restore();
        };

        /**
         * Request animation frame wrapper with polyfill
         * @return {function} Request animation frame method or timeout fallback
         */
        var reqAnimationFrame = (function() {
            return  window.requestAnimationFrame ||
                window.webkitRequestAnimationFrame ||
                window.mozRequestAnimationFrame ||
                function(callback) {
                    window.setTimeout(callback, 1000 / 60);
                };
        }());

        /**
         * Draw the background of the plugin including the scale and the track
         */
        var drawBackground = function() {
            if(options.scaleColor) drawScale();
            if(options.trackColor) drawCircle(options.trackColor, options.lineWidth, 1);
        };

        /**
         * Canvas accessor
         */
        this.getCanvas = function() {
            return canvas;
        };

        /**
         * Canvas 2D context 'ctx' accessor
         */
        this.getCtx = function() {
            return ctx;
        };

        /**
         * Clear the complete canvas
         */
        this.clear = function() {
            ctx.clearRect(options.size / -2, options.size / -2, options.size, options.size);
        };

        /**
         * Draw the complete chart
         * @param {number} percent Percent shown by the chart between -100 and 100
         */
        this.draw = function(percent) {
            // do we need to render a background
            if (!!options.scaleColor || !!options.trackColor) {
                // getImageData and putImageData are supported
                if (ctx.getImageData && ctx.putImageData) {
                    if (!cachedBackground) {
                        drawBackground();
                        cachedBackground = ctx.getImageData(0, 0, options.size * scaleBy, options.size * scaleBy);
                    } else {
                        ctx.putImageData(cachedBackground, 0, 0);
                    }
                } else {
                    this.clear();
                    drawBackground();
                }
            } else {
                this.clear();
            }

            ctx.lineCap = options.lineCap;

            // if barcolor is a function execute it and pass the percent as a value
            var color;
            if (typeof(options.barColor) === 'function') {
                color = options.barColor(percent);
            } else {
                color = options.barColor;
            }

            // draw bar
            drawCircle(color, options.lineWidth, percent / 100);
        }.bind(this);

        /**
         * Animate from some percent to some other percentage
         * @param {number} from Starting percentage
         * @param {number} to   Final percentage
         */
        this.animate = function(from, to) {
            var startTime = Date.now();
            options.onStart(from, to);
            var animation = function() {
                var process = Math.min(Date.now() - startTime, options.animate.duration);
                var currentValue = options.easing(this, process, from, to - from, options.animate.duration);
                this.draw(currentValue);
                options.onStep(from, to, currentValue);
                if (process >= options.animate.duration) {
                    options.onStop(from, to);
                } else {
                    reqAnimationFrame(animation);
                }
            }.bind(this);

            reqAnimationFrame(animation);
        }.bind(this);
    };

    var EasyPieChart = function(el, opts) {
        var defaultOptions = {
            barColor: '#ef1e25',
            trackColor: '#f9f9f9',
            scaleColor: '#dfe0e0',
            scaleLength: 5,
            lineCap: 'round',
            lineWidth: 3,
            size: 110,
            rotate: 0,
            animate: {
                duration: 1000,
                enabled: true
            },
            easing: function (x, t, b, c, d) { // more can be found here: http://gsgd.co.uk/sandbox/jquery/easing/
                t = t / (d/2);
                if (t < 1) {
                    return c / 2 * t * t + b;
                }
                return -c/2 * ((--t)*(t-2) - 1) + b;
            },
            onStart: function(from, to) {
                return;
            },
            onStep: function(from, to, currentValue) {
                return;
            },
            onStop: function(from, to) {
                return;
            }
        };

        // detect present renderer
        if (typeof(CanvasRenderer) !== 'undefined') {
            defaultOptions.renderer = CanvasRenderer;
        } else if (typeof(SVGRenderer) !== 'undefi/*******************************************************************************
 *              bootstrap-rtl (version 3.3.1)
 *      Author: Morteza Ansarinia (http://github.com/morteza)
 *  Created on: January 21,2015
 *     Project: bootstrap-rtl
 *   Copyright: Unlicensed Public Domain
 *******************************************************************************/

html {
  direction: rtl;
}
body {
  direction: rtl;
}
.list-unstyled {
  padding-right: 0;
  padding-left: initial;
}
.list-inline {
  padding-right: 0;
  padding-left: initial;
  margin-right: -5px;
  margin-left: 0;
}
dd {
  margin-right: 0;
  margin-left: initial;
}
@media (min-width: 768px) {
  .dl-horizontal dt {
    float: right;
    clear: right;
    text-align: left;
  }
  .dl-horizontal dd {
    margin-right: 180px;
    margin-left: 0;
  }
}
blockquote {
  border-right: 5px solid #eeeeee;
  border-left: 0;
}
.blockquote-reverse,
blockquote.pull-left {
  padding-left: 15px;
  padding-right: 0;
  border-left: 5px solid #eeeeee;
  border-right: 0;
  text-align: left;
}
.col-xs-1, .col-sm-1, .col-md-1, .col-lg-1, .col-xs-2, .col-sm-2, .col-md-2, .col-lg-2, .col-xs-3, .col-sm-3, .col-md-3, .col-lg-3, .col-xs-4, .col-sm-4, .col-md-4, .col-lg-4, .col-xs-5, .col-sm-5, .col-md-5, .col-lg-5, .col-xs-6, .col-sm-6, .col-md-6, .col-lg-6, .col-xs-7, .col-sm-7, .col-md-7, .col-lg-7, .col-xs-8, .col-sm-8, .col-md-8, .col-lg-8, .col-xs-9, .col-sm-9, .col-md-9, .col-lg-9, .col-xs-10, .col-sm-10, .col-md-10, .col-lg-10, .col-xs-11, .col-sm-11, .col-md-11, .col-lg-11, .col-xs-12, .col-sm-12, .col-md-12, .col-lg-12 {
  position: relative;
  min-height: 1px;
  padding-left: 15px;
  padding-right: 15px;
}
.col-xs-1, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9, .col-xs-10, .col-xs-11, .col-xs-12 {
  float: right;
}
.col-xs-12 {
  width: 100%;
}
.col-xs-11 {
  width: 91.66666667%;
}
.col-xs-10 {
  width: 83.33333333%;
}
.col-xs-9 {
  width: 75%;
}
.col-xs-8 {
  width: 66.66666667%;
}
.col-xs-7 {
  width: 58.33333333%;
}
.col-xs-6 {
  width: 50%;
}
.col-xs-5 {
  width: 41.66666667%;
}
.col-xs-4 {
  width: 33.33333333%;
}
.col-xs-3 {
  width: 25%;
}
.col-xs-2 {
  width: 16.66666667%;
}
.col-xs-1 {
  width: 8.33333333%;
}
.col-xs-pull-12 {
  left: 100%;
  right: auto;
}
.col-xs-pull-11 {
  left: 91.66666667%;
  right: auto;
}
.col-xs-pull-10 {
  left: 83.33333333%;
  right: auto;
}
.col-xs-pull-9 {
  left: 75%;
  right: auto;
}
.col-xs-pull-8 {
  left: 66.66666667%;
  right: auto;
}
.col-xs-pull-7 {
  left: 58.33333333%;
  right: auto;
}
.col-xs-pull-6 {
  left: 50%;
  right: auto;
}
.col-xs-pull-5 {
  left: 41.66666667%;
  right: auto;
}
.col-xs-pull-4 {
  left: 33.33333333%;
  right: auto;
}
.col-xs-pull-3 {
  left: 25%;
  right: auto;
}
.col-xs-pull-2 {
  left: 16.66666667%;
  right: auto;
}
.col-xs-pull-1 {
  left: 8.33333333%;
  right: auto;
}
.col-xs-pull-0 {
  left: auto;
  right: auto;
}
.col-xs-push-12 {
  right: 100%;
  left: 0;
}
.col-xs-push-11 {
  right: 91.66666667%;
  left: 0;
}
.col-xs-push-10 {
  right: 83.33333333%;
  left: 0;
}
.col-xs-push-9 {
  right: 75%;
  left: 0;
}
.col-xs-push-8 {
  right: 66.66666667%;
  left: 0;
}
.col-xs-push-7 {
  right: 58.33333333%;
  left: 0;
}
.col-xs-push-6 {
  right: 50%;
  left: 0;
}
.col-xs-push-5 {
  right: 41.66666667%;
  left: 0;
}
.col-xs-push-4 {
  right: 33.33333333%;
  left: 0;
}
.col-xs-push-3 {
  right: 25%;
  left: 0;
}
.col-xs-push-2 {
  right: 16.66666667%;
  left: 0;
}
.col-xs-push-1 {
  right: 8.33333333%;
  left: 0;
}
.col-xs-push-0 {
  right: auto;
  left: 0;
}
.col-xs-offset-12 {
  margin-right: 100%;
  margin-left: 0;
}
.col-xs-off