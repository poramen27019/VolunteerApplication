<?php
    include("connect.php");
    $event_id = $_POST['event_id'];
    $member_id = $_POST['member_id'];
    

   
    
    
        $sql ="INSERT INTO member_interest_event (event_id) 
        VALUES ('$event_id')";

        $result = mysqli_query($con,$sql);

    mysqli_close($con);
    
    ?>