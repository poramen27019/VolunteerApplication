<?php
    include("connect.php");

    $event_id = $_GET['event_id'];
    $member_id = $_GET['member_id'];
    
    // $member_id = '1';
 
        $sql = "SELECT * FROM member_interest_event 
        INNER JOIN member ON member_interest_event.member_id = member.member_id
        WHERE member_interest_event.event_id ='$event_id' AND member_interest_event.member_id ='$member_id'";

       
        $result = array();
        $query = mysqli_query($con,$sql);

        while($row = mysqli_fetch_assoc($query)){
        array_push($result,array("member_id" => $row["member_id"],
        "member_fname" => $row["member_fname"],
        "member_lname" => $row["member_lname"],
        "event_id" => $row["event_id"],
    ));
                               

    }

    print json_encode(array('result' => $result));

    mysqli_close($con);
    
    ?>


    