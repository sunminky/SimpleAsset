/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const { FileSystemWallet, Gateway } = require('fabric-network');
const path = require('path');

const ccpPath = path.resolve(__dirname, '/home/hyperledger', 'fabric-samples', 'first-network', 'connection-org1.json');

const main = async function main(amount) {
    try {

        // Create a new file system based wallet for managing identities.
        const walletPath = path.join(process.cwd(), 'wallet');
        const wallet = new FileSystemWallet(walletPath);
        console.log(`Wallet path: ${walletPath}`);

        // Check to see if we've already enrolled the user.
        const userExists = await wallet.exists('admin');
        if (!userExists) {
            console.log(`An identity for the user ${'admin'} does not exist in the wallet`);
            console.log('Run the registerUser.js application before retrying');
            return `An identity for the user ${'admin'} does not exist in the wallet\nRun the registerUser.js application before retrying`;
        }

        // Create a new gateway for connecting to our peer node.
        const gateway = new Gateway();
        await gateway.connect(ccpPath, { wallet, identity: 'admin', discovery: { enabled: true, asLocalhost: true } });

        // Get the network (channel) our contract is deployed to.
        const network = await gateway.getNetwork('mychannel');

        // Get the contract from the network.
        const contract = network.getContract('mycc2');

        // Submit the specified transaction.
        // createCar transaction - requires 5 argument, ex: ('createCar', 'CAR12', 'Honda', 'Accord', 'Black', 'Tom')
        // changeCarOwner transaction - requires 2 args , ex: ('changeCarOwner', 'CAR10', 'Dave')
        const result = await contract.submitTransaction("money_issuance", amount);
        console.log(`Transaction has been submitted. result is: ${result.toString()}`);
        return `Transaction has been submitted. result is: ${result.toString()}`

        // Disconnect from the gateway.
        await gateway.disconnect();

    } catch (error) {
        console.error(`Failed to submit transaction: ${error}`);
        // process.exit(1);
        return `Failed to submit transaction: ${error}`
    }
}

// main();

module.exports = main
