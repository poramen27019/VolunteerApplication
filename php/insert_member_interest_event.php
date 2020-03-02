<?php
    include("connect.php");
    $event_id = $_POST['interest_event_id'];
    $member_id = $_POST['interest_member_id'];
    

   
    
    
        $sql ="INSERT INTO member_interest_event (event_id,member_id) 
        VALUES ('$event_id','$member_id')";

        $result = mysqli_query($con,$sql);

    mysqli_close($con);
    
    ?>