import React, { useEffect, useState } from 'react';

function WeaponList() {
  const [weapons, setWeapons] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    fetch('http://localhost:8080/weapon')
      .then(res => res.json())
      .then(data => {
        setWeapons(data);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Ładowanie broni...</p>;

  return (
    <div>
      <h2 className="fantasy-header">Bronie</h2>
      <ul style={{ listStyle: "none", padding: 0 }}>
        {weapons.map(weapon => (
          <li key={weapon.id} className="fantasy-card">
            <h3>
              {weapon.name}
              <span style={{ fontSize: "0.8em", color: "#b88c4c" }}>
                {' '}({weapon.rarity})
              </span>
            </h3>
            <div><span className="fantasy-label">Opis:</span> {weapon.description}</div>
            <div><span className="fantasy-label">Obrażenia:</span> {weapon.damage}</div>
            {weapon.statistic &&
              <div><span className="fantasy-label">Statystyki:</span> {weapon.statistic}</div>
            }
            {weapon.specialBuffs &&
              <div><span className="fantasy-label">Dodatkowe efekty:</span> {weapon.specialBuffs}</div>
            }
          </li>
        ))}
      </ul>
    </div>
  );
}

export default WeaponList;