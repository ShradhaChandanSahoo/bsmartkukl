
	   
	function test (labelValue) {

		 if (labelValue >= 1000000) {
		        return (labelValue / 1000000).toFixed(1).replace(/\.0$/, '') + 'M';
		     }
		 if (labelValue >= 100000) {
		        return (labelValue / 100000).toFixed(1).replace(/\.0$/, '') + 'Lacs';
		     }
		     if (labelValue >= 1000) {
		        return (labelValue / 1000).toFixed(1).replace(/\.0$/, '') + 'K';
		     }
		     return labelValue;
	}

