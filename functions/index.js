const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.moveClient = functions.database.ref('/Companies/{company}/Campaigns/{campaign}')
    .onWrite(event => {
        const campaign = event.data;
        var callRef = campaign.adminRef.child('Calls').child('{call}');
        var clientsRef = campaign.adminRef.child('Clients').child('{client}').child('phoneNumber');
        // return clientsRef.once('value').then((snapshot) => {
        //     console.log('client\'s phone number: ' + snapshot.val()); 
        // });
        return callRef.once('value').then((snapshot) => {
            console.log('call phone number: ' + snapshot.child('phoneNumber').val());
        });
        
        //get call's phone number
        // var callPhoneNumber = call.child('phoneNumber').val(); 
        // console.log('call phone number: ' + callPhoneNumber); 
        //get call result
        // var callResult = call.child('callResult').val();
        // console.log('call result: ' + callResult); 
        //get client's phone number
        //var clientPhoneNumber = clientsRef.child('phoneNumber').val(); 
        //console.log('client\'s phone number: ' + clientPhoneNumber); 
        
        //move client
        /*
         * successful call == 0
         * call back == 1
         * don't call == 2
         */
    
    });