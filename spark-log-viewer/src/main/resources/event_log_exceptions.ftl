<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>${title}</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"></link>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/dataTables.bootstrap4.min.css"></link>

    <style>
        .tabs-orange {
            background-color: #f57c00 !important;
        }
        a {
            color: white;
            text-decoration: none;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
   <!-- <script src="appData.js"></script> -->

</head>

<body class="container-fluid">
   <!-- Classic tabs -->
   <div class="classic-tabs mx-2">

     <ul class="nav tabs-orange" id="myClassicTabOrange" style="margin-bottom: 2px;" role="tablist">

       <a href="#" class="brand navbar-brand">
         <img src="data:image/png;base64,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"/>
         <span id="appVersion" class="version"></span>
       </a>

       <li class="nav-item">
         <a class="nav-link  waves-light active show" id="profile-tab-classic-orange" data-toggle="tab" href="#profile-classic-orange"
           role="tab" aria-controls="profile-classic-orange" aria-selected="true"><i class="fas fa-user fa-2x pb-2"
             aria-hidden="true"></i><br>Environment</a>
       </li>
       <li class="nav-item">
         <a class="nav-link waves-light" id="follow-tab-classic-orange" data-toggle="tab" href="#follow-classic-orange"
           role="tab" aria-controls="follow-classic-orange" aria-selected="false"><i class="fas fa-heart fa-2x pb-2"
             aria-hidden="true"></i><br>Jobs</a>
       </li>
       <li class="nav-item">
         <a class="nav-link waves-light" id="contact-tab-classic-orange" data-toggle="tab" href="#contact-classic-orange"
           role="tab" aria-controls="contact-classic-orange" aria-selected="false"><i class="fas fa-envelope fa-2x pb-2"
             aria-hidden="true"></i><br>Stages</a>
       </li>
       <li class="nav-item">
         <a class="nav-link waves-light" id="awesome-tab-classic-orange" data-toggle="tab" href="#awesome-classic-orange"
           role="tab" aria-controls="awesome-classic-orange" aria-selected="false"><i class="fas fa-star fa-2x pb-2"
             aria-hidden="true"></i><br>${title} application UI</a>
       </li>
     </ul>

     <div class="tab-content card" style="height: 800px !important;" id="myClassicTabContentOrange">

       <div class="tab-pane fade active show" style='font-size: 14px;' id="profile-classic-orange" role="tabpanel" aria-labelledby="profile-tab-classic-orange">

         <div id="application_properties_main" class="table-responsive">
            <h4>Application Properties</h4>
            <div id="application_properties"></div>
         </div>

         <div id="spark_properties_main" class="table-responsive">
            <h4>Spark Properties</h4>
            <div id="spark_properties"></div>
         </div>

         <div id="jvm_properties_main" class="table-responsive">
            <h4>System Properties</h4>
            <div id="jvm_properties"></div>
         </div>

         <div id="classpath_properties_main" class="table-responsive">
             <h4>Classpath Entries</h4>
             <div id="classpath_properties"></div>
         </div>

       </div>

       <div class="tab-pane fade" id="follow-classic-orange" role="tabpanel" aria-labelledby="follow-tab-classic-orange">
         <span>
            <h4>Job Information</h4>
            <div id="job_information"> </div>
          </span>
       </div>

       <div class="tab-pane fade" id="contact-classic-orange" role="tabpanel" aria-labelledby="contact-tab-classic-orange">
            <p>stages info </p>
       </div>

       <div class="tab-pane fade" id="awesome-classic-orange" role="tabpanel" aria-labelledby="awesome-tab-classic-orange">
            <p>
                Spark Jobs (?)
                User: root
                Total Uptime: 2.1 h
                Scheduling Mode: FIFO
                Completed Jobs: 3
            </p>
            <!--Accordion wrapper-->
            <div class="accordion md-accordion accordion-blocks" id="accordionEx78" role="tablist" aria-multiselectable="true">
                <div id="job_stage_accordion_info"></div>
            </div>
            <!--/.Accordion wrapper-->
       </div>

     </div>

   </div>
   <!-- Classic tabs -->

   <script>
   $(document).ready(function() {

       var eventInfo = jQuery.parseJSON(JSON.stringify(${eventInfo}));
       $("#appVersion").html(eventInfo.sparkVersion);

        function millisToMinutesAndSeconds(millis) {
            var minutes = Math.floor(millis / 60000);
            var hours = Math.floor(minutes / 60);
            var seconds = ((millis % 60000) / 1000).toFixed(0);

            if(hours > 0) {
                return hours + ":" + minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
            } else {
                return minutes + " mins " + (seconds < 10 ? '0' : '') + seconds +" secs";
            }
        }

       function buildAccordion(appProperties, tableId) {
            var content ="";
            appProperties.forEach(function(arrayValue, index) {
                var jobId = arrayValue["Job ID"];
                var startTime = arrayValue["Submission Time"];
                var endTime = arrayValue["endTime"];
                var jobResult = arrayValue["jobResult"];
                var totalTimeTaken = endTime - startTime;
                var elapsedTime = millisToMinutesAndSeconds(totalTimeTaken);

                var statusColor = "btn btn-danger";
                if(jobResult == null) {
                    statusColor = "btn btn-warning";
                } else if(jobResult == "JobSucceeded") {
                    statusColor = "btn btn-success";
                }

                content+=   "<!-- Accordion card -->\n" +
                            "<div class=\"card\">\n" +
                            "  <!-- Card header -->\n" +
                            "  <div class=\"card-header "+statusColor+"\" role=\"tab\" id=\"heading"+jobId+"\">\n" +
                            "   <!-- Heading -->\n" +
                            "   <a data-toggle=\"collapse\" data-parent=\"#accordionEx78\" href=\"#collapse"+jobId+"\" aria-expanded=\"true\" aria-controls=\"collapse"+jobId+"\">\n" +
                            "   <h5 class=\"mt-1 mb-0\">\n" +
                            "       <span>JobId: " + jobId+" Elapsed Time: "+elapsedTime +"</span>\n"+
                            "           <i class=\"fas fa-angle-down rotate-icon\"></i>\n" +
                            "   </h5>\n" +
                            "   </a>\n" +
                            "  </div>\n" +
                            "  <!-- Card body Start-->\n" +
                            "  <div id='collapse"+jobId+"' class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading"+jobId+"\" data-parent=\"#accordionEx78\">\n" +
                            "    <div class=\"card-body\">\n" +
                            "      card-body\n" + jobId +
                            "    </div> \n" +
                            "  </div> \n" +
                            "  <!-- Card body End-->\n" +
                            "  </div> \n" +
                            "  <!-- Accordion card End -->\n";
            });

            $('#'+tableId).append(content);
       }

       function displayProperties(appProperties, tableId) {
            var content = "<table class='table table-bordered table-condensed table-striped sortable'>";

            var isArray = Array.isArray(appProperties);

            if(tableId === 'classpath_properties') {
              content += "<thead><tr><th width='50.0%'>Resource</th><th width='50.0%'>Source</th></tr></thead><tbody>";
            } else if(!isArray) {
              content += "<thead><tr><th width='50.0%'>Name</th><th width='50.0%'>Value</th></tr></thead><tbody>";
            }
            if(isArray) {

                var columns = [];
                appProperties.forEach(function(arrayValue, index) {
                    if(index == 0) {
                        content += "<thead><tr>";
                        columns = Object.keys(arrayValue);
                        columns.forEach(function (key) {
                           content += "<th>"+key+"</th>";
                        });
                        content += "</tr></thead><tbody>";
                    }

                    content += '<tr scope="row">';
                    $.each(arrayValue, function(key, value) {
                        if(key.indexOf('Time') !== -1) {
                            value = new Date(value).toUTCString();
                        }
                        content += '<td>' + value +'</td>';
                    });
                    content += '</tr>';
                });

            } else {
                $.each(appProperties, function(key, value) {
                    if(key.indexOf('Time') !== -1) {
                        value = new Date(value).toUTCString(); ;
                    }
                    content += '<tr scope="row"><td>' + key + '</td><td>'+ value +'</td></tr>';
                });
            }

            content += "</tbody></table>";
            $('#'+tableId).append(content);
       }

       var appProperties = eventInfo.appProperties;
       var sparkProperties = eventInfo.sparkProperties;
       var jvmProperties = eventInfo.jvmInformation;
       var classpathProperties = eventInfo.classpathEntries;
       var jobInformation = eventInfo.jobInfoCollection;

       displayProperties(appProperties, 'application_properties');
       displayProperties(sparkProperties, 'spark_properties');
       displayProperties(jvmProperties, 'jvm_properties');
       displayProperties(classpathProperties, 'classpath_properties');
       buildAccordion(jobInformation, 'job_stage_accordion_info');

      // $('#eventMessages').DataTable();

       $('#myTab li:first-child a').tab('show');

   } );

   </script>
</body>
</html>