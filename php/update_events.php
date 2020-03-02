<?php
    include("connect.php");

    $event_org = $_POST['event_org'];
    $event_name = $_POST['event_name'];
    $event_detail= $_POST['event_detail'];
    $event_location = $_POST['event_location'];
    $event_date = $_POST['event_date'];
    $event_type= $_POST['event_type'];
    $member_phone= $_POST['member_phone'];


    $event_id = $_POST['event_id'];
    
        $sql ="UPDATE events INNER JOIN 
        member ON events.event_member_id = member.member_id

        SET event_org='$event_org',
            event_name='$event_name',
            event_detail='$event_detail',
            event_location='$event_location',
            event_date='$event_date',
            event_type='$event_type',
            member_phone='$member_phone'

        WHERE event_id = '$event_id' " ;
       
        $result = mysqli_query($con,$sql);

    mysqli_close($con);
    
    ?>


    

UPDATE T1, T2,
[INNER JOIN | LEFT JOIN] T1 ON T1.C1 = T2. C1
SET T1.C2 = T2.C2, 
    T2.C3 = expr
WHERE condition