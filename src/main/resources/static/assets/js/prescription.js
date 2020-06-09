document.title = "Prescription List";


$(document).ready(function() {
	$('#overlay').fadeOut();
	
	/*$('#prescriptionTable').DataTable({
        dom : "'<'col-sm-3'l><'col-sm-3 text-center'><'col-sm-3'>Bfrtip",
        buttons : [ 'copyHtml5', 'excelHtml5', 'csvHtml5', 'pdfHtml5' ],
        aaSorting: [ [1, 'desc'] ]
    });*/

   /* $('#prescriptionTable').DataTable( {
        dom: 'Bfrtip',
        buttons: [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ]
    } );*/

    var startOfMonth = moment().startOf('month').format('YYYY-MM-DD hh:mm');
    var endOfMonth   = moment().endOf('month').format('YYYY-MM-DD hh:mm');

    console.log("startOfMonth: "+ startOfMonth +  " endOfMonth: "+ endOfMonth);
	var url = new URL(window.location.href);
	var range = url.searchParams.get("range");
	var startDate, endDate;
	if(range==null){
		startDate = moment().startOf('month').format('YYYY-MM-DD');
		endDate = moment().endOf('month').format('YYYY-MM-DD');
    }else{
		startDate =moment(range.split("_")[0]);
		endDate =moment(range.split("_")[1]);
	}

	$('#pmsDateRangePicker').daterangepicker(
		{
			opens : 'left',
			startDate : startDate,
			endDate : endDate,
			locale : { format : 'YYYY-MM-DD' }
		},
		function(start, end, label) {
			var startDate = start.format('YYYY-MM-DD');
			var endDate = end.format('YYYY-MM-DD');
			console.log("A new date selection was made: " + startDate + ' to ' + endDate);
			var range = "?range=" + startDate + '_' + endDate;
			window.location.replace(window.location.href .split("?")[0]+ range);
		});

});


