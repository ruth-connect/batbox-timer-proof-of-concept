$(document).ready(function() {
	poll();
});

function poll() {
	setTimeout(function() {
		$.ajax({
			url: "http://localhost:61454/reportBoilerStatus",
			success: function(result) {
				if (result.temperature != null) {
					$(".temperature").text(result.temperature);
				}
				
				if (result.humidity != null) {
					$(".humidity").text(result.humidity);
				}
				
				$(".timer").text(result.timerOn ? "ON" : "OFF");
				if (result.timerOn) {
					$(".timer").addClass("on");
				} else {
					$(".timer").removeClass("on");
				}
				
				$(".hotWater").text(result.hotWaterOn ? "ON" : "OFF");
				if (result.hotWaterOn) {
					$(".hotWater").addClass("on");
				} else {
					$(".hotWater").removeClass("on");
				}
				
				$(".heating").text(result.heatingOn ? "ON" : "OFF");
				if (result.heatingOn) {
					$(".heating").addClass("on");
				} else {
					$(".heating").removeClass("on");
				}
				
				$(".targetTemperature").val(result.targetTemperature.replace(".", "/"));
				
				poll();
			}
		});
	}, 200);
	
	$(".hotWaterOff").click(function() {
		$.ajax({
			url: "http://localhost:61454/hotWater/off"
		});
	});
	
	$(".hotWaterTimer").click(function() {
		$.ajax({
			url: "http://localhost:61454/hotWater/timer"
		});
	});
	
	$(".hotWaterOn").click(function() {
		$.ajax({
			url: "http://localhost:61454/hotWater/on"
		})
	});
	
	$(".heatingOff").click(function() {
		$.ajax({
			url: "http://localhost:61454/heating/off"
		});
	});
	
	$(".heatingTimer").click(function() {
		$.ajax({
			url: "http://localhost:61454/heating/timer"
		});
	});
	
	$(".heatingOn").click(function() {
		$.ajax({
			url: "http://localhost:61454/heating/on"
		})
	});
	
	$(".targetTemperature").change(function() {
		$.ajax({
			url: "http://localhost:61454/setTargetTemperature/" + $(".targetTemperature").val()
		})
	});
}