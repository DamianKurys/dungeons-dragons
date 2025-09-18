import React, { useState } from 'react';
import MonstersList from './MonstersList';
import ArmorList from './ArmorList';
import WeaponList from './WeaponList';
import BossesView from './BossesView_OLD.jsx';
import BossesView_OLD from './BossesView_OLD.jsx';
console.log('BossesView typeof:', typeof BossesView); 
console.log('BossesView:', BossesView);
console.log('BossesView.default:', BossesView?.default);


const REGIONS = [
  "FEROXAR", "DANVHEIM", "AUREA_IMPERIUM_LUCIS", "HAMMERGRIM",
  "ARETUZA", "WILDLANDS", "SILMARIL", "AEN_ELLE", "VENTIRO",
  "WALLACHIA", "CELVIC", "MAHABRE", "LAURAN", "ELGAE", "PHLEGETHOS"
];


function App() {
  const [selectedRegion, setSelectedRegion] = useState(REGIONS[0]);
  const [view, setView] = useState('monsters');

  return (
    <div>
      {/*Przełącznik*/}
      <div className="fantasy-menu">
        <button
          className={`fantasy-btn${view === 'monsters' ? ' active' : ''}`}
          onClick={() => setView('monsters')}
        >Potwory</button>
        <button
          className={`fantasy-btn${view === 'armor' ? ' active' : ''}`}
          onClick={() => setView('armor')}
        >Zbroje</button>
        <button
          className={`fantasy-btn${view === 'weapons' ? ' active' : ''}`}
          onClick={() => setView('weapons')}
        >Bronie</button>
        <button 
        className={`fantasy-btn${view==='bosses'?' active':''}`}
           onClick={() => setView('bosses')}
           >Bossowie</button>
      </div>

      {/*Przełącznik regionu tylko dla potworów */}
      {(view === 'monsters' || view === 'bosses') && (
        <select
          className="fantasy-select"
          value={selectedRegion}
          onChange={e => setSelectedRegion(e.target.value)}
        >
          {REGIONS.map(region =>
            <option key={region} value={region}>{region}</option>
          )}
        </select>
      )}

  
      {view === 'monsters' && <MonstersList region={selectedRegion} />}
      {view === 'bosses' && <BossesView_OLD region={selectedRegion} />}
      {view === 'armor' && <ArmorList />}
      {view === 'weapons' && <WeaponList />}
    </div>
  );
  

  
}
export default App;