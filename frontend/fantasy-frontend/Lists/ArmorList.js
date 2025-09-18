import React, { useEffect, useState } from 'react';

function ArmorList() {
  const [armors, setArmors] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    fetch('http://localhost:8080/armor')
      .then(res => res.json())
      .then(data => {
        setArmors(data);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Ładowanie zbroi...</p>;

  return (
    <div>
      <h2 className="fantasy-header">Zbroje</h2>
      <ul style={{ listStyle: "none", padding: 0 }}>
        {armors.map(armor => (
          <li key={armor.id} className="fantasy-card">
            <h3>
              {armor.name}
              <span style={{ fontSize: "0.8em", color: "#b88c4c" }}>
                {' '}({armor.rarity})
              </span>
            </h3>
            <div><span className="fantasy-label">Opis:</span> {armor.description}</div>
            {armor.statistic &&
              <div><span className="fantasy-label">Statystyki:</span> {armor.statistic}</div>
            }
            {armor.specialBuffs &&
              <div><span className="fantasy-label">Dodatkowe efekty:</span> {armor.specialBuffs}</div>
            }
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ArmorList;