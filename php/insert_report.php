<?php
    include("connect.php");
    $report1 = $_POST['report1'];
    $report2 = $_POST['report2'];
    $member_id = $_POST['member_id'];
    $event_id = $_POST['event_id'];
    $date_now = date_create()->format('Y-m-d H:i:s');
   
    
    
        $sql ="INSERT INTO report_event (report_id,report_datetime,report_detail1,report_detail2,event_id,member_id) 
        VALUES ('$report_id','$date_now','$report1','$report2','$event_id','$member_id')";
        $result = mysqli_query($con,$sql);
        mysqli_close($con);
    
    ?>