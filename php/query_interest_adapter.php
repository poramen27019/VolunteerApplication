<?php
    include("connect.php");

    $member_id = $_GET['member_id'];
    // $event_name = $_GET['event_name'];
    // $event_detail= $_GET['event_detail'];


        // $sql = "SELECT * FROM member_join_event WHERE member_id='$member_id'";
        $sql = " SELECT * FROM member_interest_event 
        INNER JOIN member ON member_interest_event.member_id = member.member_id
        INNER JOIN events ON member_interest_event.event_id =  events.event_id 
        WHERE member_interest_event.member_id='$member_id'"
        ;

       
        $result = array();
        $query = mysqli_query($con,$sql);

        while($row = mysqli_fetch_assoc($query)){
        array_push($result,array("event_id" => $row["event_id"],
        "event_name" => $row["event_name"],
        "event_location" => $row["event_location"], 
        "event_date" => $row["event_date"],
        "event_type" => $row["event_type"],
        "event_org" => $row["event_org"],
        "event_detail" => $row["event_detail"],
        "member_fname" => $row["member_fname"],
        "member_phone" => $row["member_phone"],
        "event_logo" => $row["event_logo"],             
                            ));
                               

    }

    print json_encode(array('result' => $result));

    mysqli_close($con);
    
    ?>


    