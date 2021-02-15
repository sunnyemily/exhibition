var metrics,info,health;
function loadMetrics(){
	$.get("/manage/metrics",function(data){
		metrics = data;
		$("#cpuCount").html(metrics.processors);
		$("#memerySize").html((metrics.mem/1024/1024).toFixed(2)+"G");
	});
}
function loadInfo(){
	$.get("/manage/info",function(data){
		info = data;
		$("#appName").html(info.app.name);
		$("#appVersion").html(info.app.version);
		$("#appDescription").html(info.app.description);
	});
}
function loadHealth(){
	$.get("/manage/health",function(data){
		health = data;
		showDiskInfo();
	});
}
//Flot Pie Chart
$(function() {
	loadInfo();
	loadHealth();
	loadMetrics();
});
function showDiskInfo(){
	$("#diskSize").html((health.diskSpace.total/1024/1024/1024).toFixed(2)+"G");
    var data = [{
        label: "已使用空间",
        data: health.diskSpace.total-health.diskSpace.free,
        color: "red",
    }, {
        label: "未使用空间",
        data: health.diskSpace.free,
        color: "#f0f0f0",
    }];
    var plotObj = $.plot($("#flot-pie-chart"), data, {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
            shifts: {
                x: 20,
                y: 0
            },
            defaultTheme: false
        }
    });
}
$(function() {

    var container = $("#flot-line-chart-moving");

    // Determine how many data points to keep based on the placeholder's initial size;
    // this gives us a nice high-res plot while avoiding more than one point per pixel.

    var maximum = container.outerWidth() / 2 || 300;

    //

    var data = [];

    function getRandomData() {
		y = metrics?(metrics["mem.free"]/metrics.mem*100).toFixed(0):0;
        if (data.length) {
            data = data.slice(1);
        }

        while (data.length < maximum) {
			data.push(y);
        }

        // zip the generated y values with the x values

        var res = [];
        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }

        return res;
    }

    //

    series = [{
        data: getRandomData(),
        lines: {
            fill: true
        }
    }];

    //

    var plot = $.plot(container, series, {
        grid: {

            color: "#999999",
            tickColor: "#D4D4D4",
            borderWidth:0,
            minBorderMargin: 20,
            labelMargin: 10,
            backgroundColor: {
                colors: ["#ffffff", "#ffffff"]
            },
            margin: {
                top: 8,
                bottom: 20,
                left: 20
            },
            markings: function(axes) {
                var markings = [];
                var xaxis = axes.xaxis;
                for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
                    markings.push({
                        xaxis: {
                            from: x,
                            to: x + xaxis.tickSize
                        },
                        color: "#fff"
                    });
                }
                return markings;
            }
        },
        colors: ["#1ab394"],
        xaxis: {
            tickFormatter: function() {
                return "";
            }
        },
        yaxis: {
            min: 0,
            max: 110
        },
        legend: {
            show: true
        }
    });

    // Update the random dataset at 25FPS for a smoothly-animating chart

    setInterval(function updateRandom() {
		loadMetrics();
        series[0].data = getRandomData();
        plot.setData(series);
        plot.draw();
    }, 1000);

});




