call kubectl apply -f 1_keycloak.yml
call kubectl apply -f 2_configmaps.yml
call kubectl apply -f 3_configserver.yml
call kubectl apply -f 4_eurekaserver.yml
call kubectl apply -f 5_accounts.yml
call kubectl apply -f 6_loans.yml
call kubectl apply -f 7_cards.yml
call kubectl apply -f 8_gatewayserver.yml