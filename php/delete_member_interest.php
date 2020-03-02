<?php
    include("connect.php");

    $event_member_id = $_POST['member_id'];
    $event_event_id = $_POST['event_id'];

    // $event_member_id = '1';
    // $event_event_id = '1';
    
        $sql ="DELETE FROM member_interest_event WHERE member_id='$event_member_id' AND event_id='$event_event_id'";

        $result = mysqli_query($con,$sql);

    mysqli_close($con);
    
    ?>