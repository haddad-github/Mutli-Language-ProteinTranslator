//import modules
const { parse } = require('csv-parse') //parsing csv
const fs = require('fs') //read from file system
const ps = require('prompt-sync') //get user input

//path
const file = "C:\\Users\\rorop\\Desktop\\Script_In_All_Languages\\codon_table.csv"

//read from csv and append to list, as a list of lists
function createReadStream() {
    var csvData = [];
    return new Promise(resolve => {
        fs.createReadStream(file)
            //delimiter = ',' and ignore header by starting at line 2
            .pipe(parse({delimiter: ',', from_line: 2}))
            .on('data', function (csvrow) {
                // console.log(csvrow);
                csvData.push(csvrow);
            })
            .on('end', () => resolve(csvData));
    });
}

//create dictionary from list of list
function create_dict(list_of_lists, key_index, value_index){
    var dict = {}
    for (i = 0; i < list_of_lists.length; i++){
        dict[list_of_lists[i][key_index]] = list_of_lists[i][value_index]
    }
    return dict
}

//clean dict; replace "Stop" by "_"
function clean_dict(dict){
    //TODO understand this script
    Object.keys(dict)
        .forEach(function(key) {
            if (dict[key] == 'Stop') {
                dict[key] = '_'
            }
        }
    )
    return dict
}

//get user input
function get_user_seq(){
    const prompt = ps()
    let seq = prompt('Enter RNA sequence: ')

    seq = seq.toUpperCase()

    var accepted = ['A', 'U', 'C', 'G']

    for (const character of seq) {
        if (!accepted.includes(character)) {
            throw new Error('Only (A,U,C,G) are accepted');
        }
    }
    return seq
}

//slice into codons
function chunkString (str, len) {
    //TODO understand this script
    const size = Math.ceil(str.length/len)
    const r = Array(size)
    let offset = 0
    for (let i = 0; i < size; i++) {
        r[i] = str.substr(offset, len)
        offset += len
    }
    return r
}

//translate
function translate(seq, dict){
    var protein = ""

    for (i=0; i < seq.length; i++){
        protein += dict[seq[i]]
    }
    return protein
}


async function main() {
    const csvData = await createReadStream();

    // LOGIC AFTER GETTING DATA
    var amino_dict = create_dict(csvData, 0, 2)
    var amino_clean_dict = clean_dict(amino_dict)
    var seq = get_user_seq()
    var seq_chunked = chunkString(seq, 3)
    console.log(translate(seq_chunked, amino_clean_dict))

}

main()

