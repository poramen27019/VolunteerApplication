<?php
    include("connect.php");

    // $member_id = $_GET['member_id'];
    $event_id = $_GET['event_id'];
    // $event_name = $_GET['event_name'];
    // $event_detail= $_GET['event_detail'];
    // $member_id = '4';
    // $event_id = '1';


        $sql = "SELECT * FROM member_join_event 
        INNER JOIN member ON member_join_event.member_id = member.member_id
        WHERE event_id ='$event_id' ";

       
        $result = array();
        $query = mysqli_query($con,$sql);

        while($row = mysqli_fetch_assoc($query)){
        array_push($result,array("member_id" => $row["member_id"],
        "member_fname" => $row["member_fname"],
        "member_lname" => $row["member_lname"],
        "member_phone" => $row["member_phone"],
    ));
                               

    }

    print json_encode(array('result' => $result));

    mysqli_close($con);
    
    ?>


    