
let player = {
    nom: "John Doe",
    pointsDeVie: 100,
    force: 12,
    dexterite: 8,
    vitesse: 6,
    initiative: 15,
    arme: "Épée longue",
    armure: "Cotte de mailles",
};

function updatePlayerInfo() {
    document.getElementById("player-name").textContent = player.nom;
    document.getElementById("player-hp").textContent = player.pointsDeVie;
}

function movePlayer() {
    console.log(`${player.nom} se déplace !`);
    alert(`${player.nom} se déplace sur la carte.`);
}

function attackPlayer() {
    console.log(`${player.nom} attaque avec ${player.arme}!`);
    alert(`${player.nom} attaque avec ${player.arme}!`);
}

function equipPlayer() {
    console.log(`${player.nom} s'équipe d'une nouvelle arme !`);
    alert(`${player.nom} s'équipe d'une épée longue et d'une armure légère.`);
    player.arme = "Épée longue";
    player.armure = "Armure légère";
    updatePlayerInfo();
}

function action(actionType) {
    switch (actionType) {
        case 'move':
            movePlayer();
            break;
        case 'attack':
            attackPlayer();
            break;
        case 'equip':
            equipPlayer();
            break;
        default:
            console.log("Action inconnue !");
            break;
    }
}

updatePlayerInfo();
