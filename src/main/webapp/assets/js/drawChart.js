$(document).ready(() => {
	drawChartOne();
	drawChartTwo();
	drawChartThree();
});

//<![CDATA[
const setBg = () => {
	const letters = 'BCDEF'.split('');
    let color = '#';
    for(let e = 0; e < 6; e++) {
        color += letters[Math.floor(Math.random() * letters.length)];
    }
    return color;
}
//]]>
					
const drawChartOne = () => {
	let dataColor = [];
	
	data1.forEach(data => {
		dataColor.push(setBg());
	});
	
	let ctx = document.getElementById('chartOne').getContext('2d');
	let chart = new Chart(ctx, {
		type: "pie",
		data: {
			labels: dataLabel1,
			datasets: [{
				label: '$',
				data: data1,
				 backgroundColor: dataColor
			}]
		}, 
		options: {
	    	responsive: true
	    }
	});
}

const drawChartTwo = () => {
		
	let ctx = document.getElementById('chartTwo').getContext('2d');
	let chart = new Chart(ctx, {
		type: "bar",
		data: {
			labels: convertMonthNumberToMonthNameForArray(dataLabel2),
			datasets: [{
				label: 'Spend Money',
				backgroundColor: '#837aff',
				borderColor: '#7f75ff',
				borderWidth: 1,
				data: data2
			}]
		},
		options: {
	    	responsive: true,
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }

	    }
	});
}

const drawChartThree = () => {
	let dataColor = [];
	
	data3.forEach(() => {
		dataColor.push(setBg());
	});
	
	let ctx = document.getElementById('chartThree').getContext('2d');
	let chart = new Chart(ctx, {
		type: "polarArea",
		data: {
			labels: dataLabel3,
			datasets: [{
				label: 'Spend Money',
				backgroundColor: dataColor,
				data: data3
			}]
		},
		options: {
	    	responsive: true,
			legend: {
					position: 'right',
				},
				scale: {
					ticks: {
						beginAtZero: true
					},
					reverse: false
				},
				animation: {
					animateRotate: false,
					animateScale: true
				}
	    }
	});
}

const convertMonthNumberToMonthNameForArray = (monthNumberArr) => {
	let monthNameArr = [];
	monthNumberArr.forEach(monthNumber => {
		monthNameArr.push(MONTHS[parseInt(monthNumber)]);
	});
	
	return monthNameArr;
}