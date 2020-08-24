<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<style>
body{
background-image:url(resources/images/300_water_splash_close_up.jpg);
}
.active{
color: rgb(20, 20, 20);
}
</style>
<div class="navbar navbar-inverse">

	<img src="resources/images/bsmart-water.png" alt="" height="85px;">
	<a class="navbar-brand" href="index.html"> </a> <span
		style="padding-left: 165px; white-space: nowrap; font-size: 34px; font-weight: 400; color: white; font-family: Times New Roman;">
		Kathmandu Upatyaka Khanepani Limited<sub style="font-size: 19px;">       -Save Water Save Life</sub></span>
</div>

<div class="page-container"  >



	<div class="panel panel-flat" style="background-color:rgba(255, 255, 255, 0.74);"><!--style="background-image:url(./resources/images/background.jpg);"  -->

		<div class="tabbable" >
			
			<ul class="nav nav-tabs nav-tabs-top nav-justified">
				
				<li class="active"><a href="#top-justified-tab1" data-toggle="tab"><b>HOME</b></a></li>
				<li><a href="#top-justified-tab2" data-toggle="tab"><b>ABOUT US</b></a></li>
				<li><a href="#top-justified-tab3" data-toggle="tab"><b>CONTACT US</b></a></li>

			</ul>
			
			
			<br>

			<div class="tab-content" style="padding:10px 8px;">
				
				
				<div class="tab-pane active" id="top-justified-tab1">




					<div class="row">


						<div class="col-lg-3" onclick="gotoLoginLink(1);" style="cursor: pointer;">
							<div class="panel bg-blue-400">
								<div class="panel-body">
									
									<h2 class="no-margin" align="center"><i class="glyphicon glyphicon-th"></i></h2>
									<h2 class="no-margin" align="center">Billing</h2>
									<h2 class="no-margin" align="center">Employee Login</h2>
								
									<div class="text-muted text-size-small"></div>
								</div>


							</div>
						</div>



						<div class="col-lg-3" onclick="gotoLoginLink(2);" style="cursor: pointer;">
							<div class="panel bg-teal-400">
								<div class="panel-body">
									
									<h2 class="no-margin" align="center"><i class="glyphicon glyphicon-phone-alt"></i></h2>
									<h2 class="no-margin" align="center">Customer Care </h2>
									<h2 class="no-margin" align="center">Helpline</h2>
								
									<div class="text-muted text-size-small"></div>
								</div>

								
							</div>
						</div>
						
						<div class="col-lg-3" onclick="gotoLoginLink(3);" style="cursor: pointer;">
							<div class="panel bg-orange-400">
								<div class="panel-body">
									
									<h2 class="no-margin" align="center"><i class="glyphicon glyphicon-hand-up"></i></h2>
									<h2 class="no-margin" align="center">Customer Care </h2>
									<h2 class="no-margin" align="center">Web</h2>
								
									<div class="text-muted text-size-small"></div>
								</div>

								
							</div>
						</div>

						<div class="col-lg-3" onclick="gotoLoginLink(4);">
							<div class="panel bg-pink-400">
								<div class="panel-body">
									
									<h3 class="no-margin" align="center"><i class="glyphicon glyphicon-compressed"></i></h3>
									<h2 class="no-margin" align="center">New Connection</h2>
									<h2 class="no-margin" align="center">Employee Login</h2>
								
									<div class="text-muted text-size-small"></div>
								</div>

								
							</div>
						</div>
						
						</div>
						
						
						<div class="row">




							<div class="col-lg-3" onclick="gotoLoginLink(5);" style="cursor: pointer;">
								<div class="panel bg-purple-400">
									<div class="panel-body">
										<h2 class="no-margin" align="center"><i class="glyphicon glyphicon-user"></i></h2>
										<h2 class="no-margin" align="center">Customer Portal</h2>
										<h2 class="no-margin" align="center">New Connection</h2>
									
										<div class="text-muted text-size-small"></div>
									</div>
	
									
								</div>
							</div>
							
							<div class="col-lg-3" onclick="gotoLoginLink(6);" style="cursor: pointer;">
								<div class="panel bg-green-400">
									<div class="panel-body">
										<h2 class="no-margin" align="center"><i class="glyphicon glyphicon-cutlery"></i></h2>
										<h2 class="no-margin" align="center">Inventory</h2>
										<h2 class="no-margin" align="center">Login</h2>
									
										<div class="text-muted text-size-small"></div>
									</div>
	
									
								</div>
							</div>
							
							
							<div class="col-lg-3" onclick="gotoLoginLink(7);" style="cursor: pointer;">
								<div class="panel bg-indigo-400">
									<div class="panel-body">
										<h2 class="no-margin" align="center"><i class="glyphicon glyphicon-pencil"></i></h2>
										<h2 class="no-margin" align="center">Data Validation</h2>
										<h2 class="no-margin" align="center">Employee Login</h2>
									
										<div class="text-muted text-size-small"></div>
									</div>
	
									
								</div>
							</div>
							
							<div class="col-lg-3" onclick="gotoLoginLink(8);" style="cursor: pointer;">
								<div class="panel bg-info-400">
									<div class="panel-body">
										<h2 class="no-margin" align="center"><i class="glyphicon glyphicon-user"></i></h2>
										<h2 class="no-margin" align="center">Cash Collection</h2>
										<h2 class="no-margin" align="center">Employee Login</h2>
									
										<div class="text-muted text-size-small"></div>
									</div>
	
									
						   </div>
						</div>
					</div>
					
					<div class="row" style="margin: 0px; background-color: #FFDAB9;">
						<div id="loading" class="col-sm-2"> 
            			<!-- <h3 id="loadingText">Loading..... Please wait. </h3>  -->
				 		<img class="img-responsive" alt="image" src="./resources/images/save_water.jpg.png"  >
				 		
					</div> 
					<div class="col-sm-10">
                                        <div class="filter-v1 margin-top-10" style="padding-left: 29px;">
                                            <div class="portlet light">
                                                <div class="portlet-title">
                                                    <div class="caption">


                                                        <h3><b>Save water, save energy, save money </b></h3>


                                                    </div>
                                                   
                                                </div>
                                                <div class="portlet-body" >

                                                    <p style="text-align:justify">


                                                        Did you know we use more water than any other part of the country?

                                                        Water is precious and we should use it wisely. Saving water not only makes environmental sense but it can help save energy and reduce your bills.
                                                        According to the Energy Saving Trust, 21% of a household heating bill relates to heating water for showers, baths and hot water taps.
                                                    </p>

                                                </div>
                                            </div>

                                        </div>
                                    </div>
					
					  </div>
                                
                                  
                    <div class="row" style="margin: 0px; text-align: center;">
                         <h2><b>Water saving tips that really make a difference </b></h2>
                         <!-- <img src="../StyleSheets/images/water-saving-tips.jpg" alt="logo" class="logo-default" style="width: 1162px;" /> -->
                    </div>
                    
                    <div class="row" style="margin: 0px; text-align: center;" >
                         <img  src="./resources/images/water-saving-tips.jpg.png" alt="logo" class="logo-default" style="width: 1162px;" /> 
                    </div>
					 

				</div>

				<div class="tab-pane" id="top-justified-tab2">
				
				
				
				
				<div class="row">
				
						<div class="col-lg-6" style="padding-left: 20px;">
							
							<font size="4" style="font-family: Times New Roman;"><label><strong>Kathmandu Upatyaka Khanepani Limited (KUKL)</strong> is a public company registered under the Nepal Government’s Company Act 2063 and operates under the Public Private Partnership (PPP) modality. According to KUKL's Articles of Association, the company has the objective to undertake and manage the water supply and sanitation system of the Kathmandu Valley previously operated by NWSC and to provide a quantitative, qualitative and reliable service to its customers at an affordable price.</label></font>
							
							<font size="4" style="font-family: Times New Roman;"><label>KUKL is responsible for the operation and management of water and wastewater services in the Valley. It operates the water supply and wastewater services under a License and Lease Agreement with the Kathmandu Valley Water Supply Management Board (KVWSMB) for 30 years. KUKL is responsible for the maintenance of all assets received on lease from KVWSMB. The company will also take over the responsibility for infrastructure built by the Melamchi Water Supply Project.</label></font>
							
							<font size="4" style="font-family: Times New Roman;"><label>The shareholders of the company, with relative initial shareholdings, are: the Government of Nepal (30%), Municipalities in the Valley (50%), Private Sector Organisations (15%) [ FNCCI- 3%, Lalitpur Chamber of Commerce - 1.5%, Nepal Chamber of Commerce- 9%, Bhaktapur Chamber of Commerce -1.5%], and an employee trust to be paid by the government (5%).</label></font>
							
							<font size="4" style="font-family: Times New Roman;"><label>The KUKL is governed by a Board of Directors with seven Members. Four directors are nominated by shareholders (one each from GoN, Kathmandu Metropolis, Lalitpur Sub-Metropolis and private sector organisations) and three are independently appointed. The chairman is selected from among the Board of Directors.</label></font>
						
						</div>
						
						<div class="col-lg-6" style="padding-right: 19px;">
						
						
						<table class="table datatable-basic table-bordered">
                              <tbody>
                              
                              <tr class="pagrtitle"> 
                                <td bgcolor="#D3EEFE" height="30" width="5%">&nbsp;</td>
                                <td class="download" bgcolor="#D3EEFE" width="39%">Name</td>
                                <td class="download" bgcolor="#D3EEFE" width="18%"> Position</td>
                                <td class="download" bgcolor="#D3EEFE" width="38%"> Representation 
                                  from</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF"> 
                                <td bgcolor="#0A6B96" style="color: white;" height="25"><div align="center"><strong>1.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Mr. Suresh Kumar Basnet</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Chairman</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Nepal Chamber of Commerce</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF"> 
                                <td bgcolor="#0A6B96" style="color: white;" height="25"> <div align="center"><strong>2.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Er. Mr.  Ghanashyam Bhattarai</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Government of Nepal</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF"> 
                                <td bgcolor="#0A6B96" style="color: white;" height="25"> <div align="center"><strong>3.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Mr. Rudra Singh Tamang</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Kathmandu Metropolitan City  Office</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF"> 
                                <td bgcolor="#0A6B96" style="color: white;" height="25"> <div align="center"><strong>4.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Mr. Kumar Prasad Koirala</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Government of Nepal</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF"> 
                                <td bgcolor="#0A6B96" style="color: white;" height="25"> <div align="center"><strong>5.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">  Mr. Mahesh Kumar Kafle<span id="yui_3_16_0_ym19_1_1463381061550_5330"> </span></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Kathmandu Metropolitan City Office</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF"> 
                                <td bgcolor="#0A6B96" style="color: white;" height="25"> <div align="center"><strong>6.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Er.  Mr. Rudra Prasad Gautam  </td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Lalitpur Sub-Metropolitan City Office</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF">
                                <td bgcolor="#0A6B96" style="color: white;" height="25"><div align="center"><strong>7.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Er. Mr. Kamal Raj Pande</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Independent Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Asian Development Bank</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF">
                                <td bgcolor="#0A6B96" style="color: white;" height="25"><div align="center"><strong>8.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Mr. Maheswor Bhakta Shrestha</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Independent Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Independent</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF">
                                <td bgcolor="#0A6B96" style="color: white;" height="25"><div align="center"><strong>9.</strong></div></td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Mr. Hari Gopal Shrestha</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Independent Director</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Independent</td>
                              </tr>
                              <tr class="rbd2" bgcolor="#FFFFFF">
                                <td bgcolor="#0A6B96" style="color: white;" height="25">&nbsp;</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Er. Mr. Milan Kumar Shakya</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">Company Secretary</td>
                                <td class="pagelink" bgcolor="#0A6B96" style="color: white;" height="25">&nbsp;</td>
                              </tr>
                             
                          </tbody>
                                    
                    </table>
						
						</div>
				</div><br>
				
		
			</div>
				
				
				<div class="tab-pane" id="top-justified-tab3">
				
				
				<div style="padding-left: 40px;">
				    <font size="4" style="font-family: Times New Roman;"><label>If you would like further information on the Kathmandu Upatyaka Khanepani Limited, you can contact the project office at :</label></font>
					
					<br><font size="4" style="font-family: Times New Roman;"><label><strong>Tripureswor Marga,</strong></label></font>
					<br><font size="4" style="font-family: Times New Roman;"><label><strong>Kathmandu, Nepal</strong></label></font>
					<br><font size="4" style="font-family: Times New Roman;"><label>Toll Free No 	:<strong>166001-22202</strong></label></font>
					<br><font size="4" style="font-family: Times New Roman;"><label>Telephone 	:<strong>977-1-4262202, 4262205</strong></label></font>
					<br><font size="4" style="font-family: Times New Roman;"><label>Fax 	:<strong>977-1-4259824, 4262229</strong></label></font>
					<br><font size="4" style="font-family: Times New Roman;"><label>E-mail 	:<strong>info@kathmanduwater.org </strong></label></font>
				</div>
				
				</div>

					

			</div>
		</div>
	</div>
</div>

<script>


function gotoLoginLink(value){
	
	if(value==1){
        window.open('./login','_blank');

	}else if(value==2){
        window.open('./login','_blank');

	}
	else if(value==3){
        window.open('http://grievance.kathmanduwater.com:8101','_blank');

	}
	else if(value==4){
        window.open('http://grievance.kathmanduwater.com:8101/kuklgrievance/login','_blank');

	}else if(value==5){
        window.open('http://grievance.kathmanduwater.com:8101/kuklgrievance/custumerLoginPage','_blank');

	}
	else if(value==6){
       window.open('http://www.kathmanduwater.com/login','_blank');

	}
	else if(value==7){
        window.open('http://www.kathmanduwater.com/login','_blank');

	}
	else if(value==8){
        window.open('http://www.kathmanduwater.com/login','_blank');

	}
	
	
}
</script>


<style>
.navbar-inverse {
	background-color: #0A6B96;
	border-color: #ffffff;
	height: 92px;
}
</style>