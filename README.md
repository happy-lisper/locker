# LOCKER

A simple simualtion of order delivery to Locker/InPost-Paczkomat:


## considerations

1. exceeded capacity
2. preferred and alternative lockers
3. courier places packages in wrong lockers
3. locker sizes
4. (optimization) preemptive delivery, assuming the package is picked in the meantime
5. using locker as a hub
6. returns
7. locker as a service for P2P


##  example 1

```
user=> (use 'locker :reload)
nil
user=> (def x0 (make-locker-box "x1" 3))
#'user/x0
user=> (dorun (repeatedly 5 #(make-order x0)))
nil
x1 #uuid "da63fe76-461b-42c3-a1f0-481f3db9fb3f" 
1736284122482 Order Created
x1 #uuid "1a4c1798-2b47-4470-9d4f-af40dd723208" 1736284122486 Order Created
x1 #uuid "f97d73d2-08c3-4c9c-a37d-865d5df730a9" 1736284122485 Order Created
x1 #uuid "af65cf5e-125f-4333-b681-46fb441adb0d" 1736284122486 Order Created
x1 #uuid "c77ca517-ddeb-40fe-84b1-3331ed592f3f" 1736284122484 Order Created
x1 #uuid "c77ca517-ddeb-40fe-84b1-3331ed592f3f" 1229 Order Shipped
x1 #uuid "f97d73d2-08c3-4c9c-a37d-865d5df730a9" 3722 Order Shipped
x1 #uuid "da63fe76-461b-42c3-a1f0-481f3db9fb3f" 7327 Order Shipped
x1 #uuid "c77ca517-ddeb-40fe-84b1-3331ed592f3f" 7410 Order Delivered 
x1 #uuid "1a4c1798-2b47-4470-9d4f-af40dd723208" 7554 Order Shipped
x1 #uuid "af65cf5e-125f-4333-b681-46fb441adb0d" 9979 Order Shipped
x1 #uuid "c77ca517-ddeb-40fe-84b1-3331ed592f3f" 7410 Order Picked Up done
x1 #uuid "f97d73d2-08c3-4c9c-a37d-865d5df730a9" 11503 Order Delivered 
x1 #uuid "af65cf5e-125f-4333-b681-46fb441adb0d" 14173 Order Delivered 
x1 #uuid "da63fe76-461b-42c3-a1f0-481f3db9fb3f" 15364 Order Delivered 
x1 #uuid "f97d73d2-08c3-4c9c-a37d-865d5df730a9" 11503 Order Picked Up done
x1 #uuid "1a4c1798-2b47-4470-9d4f-af40dd723208" 20599 Order Delivered IS DELAYED by 2795
x1 #uuid "da63fe76-461b-42c3-a1f0-481f3db9fb3f" 15364 Order Picked Up done
x1 #uuid "af65cf5e-125f-4333-b681-46fb441adb0d" 14173 Order Picked Up done
x1 #uuid "1a4c1798-2b47-4470-9d4f-af40dd723208" 20599 Order Picked Up done
user=> 

```

## example 2
```
user=> (binding [*skip-factor* 10000] (let [x1 (make-locker-box "x1" 30000) x2 (make-locker-box "x2" 15000)]
  #_=>                          (dorun (repeatedly 70000 #(make-order (rand-nth [x1 x2]))))))
x1 #uuid "2d9b9c06-c812-4288-89d9-b46f20455f14" 1736284184127 Order Created
nil
x2 #uuid "6700d649-e913-427f-831a-7c71e766fc7e" 1736284184192 Order Created
x1 #uuid "0986e3bf-9270-46e6-8808-d3474a2bf147" 1736284184206 Order Created
x2 #uuid "fa3af5a8-94d6-457c-9bdd-76765887c2a5" 1736284184207 Order Created
x1 #uuid "0f09b024-0d71-41dd-be6d-36639fbf5302" 1736284184210 Order Created
x1 #uuid "ee01fb1d-30c6-4d0d-b0b3-434c17e6d836" 1736284184221 Order Created
x1 #uuid "3e7cb522-cca7-420f-b1c4-ed925ba05b34" 1736284184221 Order Created
x2 #uuid "2b7b83fb-b347-41df-8ec7-bf73fa4a17db" 1736284184225 Order Created
x1 #uuid "a37758e0-4121-425b-9afe-a4b6dea58e86" 1320 Order Shipped
x2 #uuid "16a5a91f-6ea1-451a-af74-bc1c502f4bad" 2227 Order Shipped
x2 #uuid "82032723-480b-401b-97cf-bfb9f1001362" 3458 Order Shipped
x2 #uuid "a85919a7-2731-4d9e-b0fc-af77511a57ec" 5090 Order Delivered 
x2 #uuid "b7a53442-1c6a-4a70-a90e-5efcb9bb290e" 5539 Order Shipped
x2 #uuid "690806c4-c90e-4f82-b429-423e6bd21df1" 5631 Order Delivered 
x1 #uuid "c8b6d1e7-eb82-487d-ac0c-c356327162f3" 6156 Order Shipped
x1 #uuid "16b2855f-4572-4c7e-b19f-57ab6037cf76" 7233 Order Shipped
x2 #uuid "051ef5b9-acc1-4c56-9fcc-e054a812f4fb" 7209 Order Shipped
x2 #uuid "a0a58b6e-cb8f-47da-82ab-99556d4ff843" 7325 Order Shipped
x1 #uuid "257a6e7e-9a91-4174-8d56-362ebbc12e2d" 7849 Order Shipped
x2 #uuid "d5ad2f11-89d1-42a4-8397-4666e10bed98" 7957 Order Shipped
x2 #uuid "6ca4adcd-e403-47b0-a69d-1166e2a38704" 8081 Order Shipped
x2 #uuid "aa2f1d7f-5b7c-40c4-bed7-812cf287c54f" 8701 Order Delivered 
x2 #uuid "9024a2f8-9230-4ed5-99e7-6b2cd73252b5" 9043 Order Shipped
x2 #uuid "a838365f-3ddf-49ec-8c7b-93bfeac61102" 9622 Order Shipped
x1 #uuid "a8b8af71-e7a4-4d4b-a54e-9854c36b4324" 10539 Order Delivered 
x1 #uuid "34b35356-3c42-4c22-a64a-5279e661e598" 11739 Order Delivered 
x1 #uuid "b1a77e4c-7c18-4d8c-a6c1-e0e81f96329e" 12779 Order Delivered 
x2 #uuid "bdac5c53-1a69-495d-8830-df39da3de698" 6980 Order Picked Up done
x1 #uuid "dabfa35c-3bfb-4849-bb7f-254603e16959" 15063 Order Delivered 
x1 #uuid "e206f97f-75ee-47e0-b72d-7e480c401c79" 9127 Order Picked Up done
x2 #uuid "734b93f8-2706-4e8e-addd-7748d0f757a2" 12878 Order Picked Up done
x1 #uuid "75c55ecb-0421-4150-a7c1-79827a279027" 12694 Order Picked Up done
x2 #uuid "e08016d0-8649-42e2-bd4d-12487c5ddf51" 17907 Order Delivered IS DELAYED by 5051
x1 #uuid "ebe042e7-e18c-4580-8401-c6a00f324cb0" 9731 Order Picked Up done
x1 #uuid "daf67073-f15e-4821-bc2c-9bcfa52afece" 15490 Order Picked Up done
x1 #uuid "cec7f300-2504-47cd-b187-7f0f20b6b420" 14960 Order Picked Up done
x2 #uuid "832f681e-cb53-400d-81ac-4a5963d91004" 18628 Order Picked Up done
x1 #uuid "ea143d43-65fb-4b5f-94ca-1f3ae7be9941" 15633 Order Picked Up done
x2 #uuid "a74f78ba-7fe7-4e5c-b1f2-443442f80bd2" 24343 Order Delivered IS DELAYED by 6857
x2 #uuid "de2d6032-5ca5-46c6-b925-f251fa6e1c43" 18804 Order Picked Up done
x2 #uuid "ff0f95ef-eabd-4922-9f37-ee353edf635c" 23317 Order Picked Up done
```